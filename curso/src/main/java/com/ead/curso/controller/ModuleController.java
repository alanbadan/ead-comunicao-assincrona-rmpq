package com.ead.curso.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.engine.transaction.jta.platform.internal.WebSphereExtendedJtaPlatform;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.curso.dto.ModuleDto;
import com.ead.curso.model.CourseModel;
import com.ead.curso.model.ModuleModel;
import com.ead.curso.services.CourseService;
import com.ead.curso.services.ModuleService;
import com.ead.curso.specification.SpecificationTemplate;

@RestController      //trabalhando a URI a nivel de metodo e não de classe senão usaria @requestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	CourseService courseService;
	
	
	@PostMapping("/course/{courseId}/modules")
	public ResponseEntity<Object>saveModule(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid ModuleDto moduleDto){
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId); //pegando o courseId e verifivcando se ele existe
		if(!courseModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not Found");
		}
		var moduleModel = new ModuleModel();
		//precisa fazer a conver~sao usando ObejctMapper ou BeansUtisl ou uasando um set simples ou metod declarado  como no courseUserModel
		BeanUtils.copyProperties(moduleDto, moduleModel); // o azul é o que vc quer converter
		moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		moduleModel.setCourse(courseModelOptional.get()); //um modulo sem esta associado a um curso (chave estrangira)
		return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
	}
	
	@DeleteMapping("/course/{courseId}/modules/{modulesId}") //passando o id do curso e id do modulo para deletar
	public ResponseEntity<Object> deleteModule (@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId){
		Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId); //metodo para ver se existe o moduloId dentro do course
		if(!moduleModelOptional.isPresent()) {                                                              //consulta especifica ao banco no repository   
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
		}
		moduleService.delete(moduleModelOptional.get());//get pq é um optional
		return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfuly");
	}
	
	@PutMapping("/course/{courseId}/module/{moduleId}")
	public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId,
			                                    @RequestBody @Valid ModuleDto moduleDto){
		Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
		if(!moduleModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
		}
		var moduleModel = moduleModelOptional.get();//pq não deu um new ?? pq vc esta atualizando um modulo dentro do curso
		moduleModel.setTitle(moduleDto.getTitle());
		moduleModel.setDescription(moduleDto.getDescription());
		return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel));		
	
/*	
 * }
	@GetMapping("/course/{courseId/module") //tendo um pathvariable para consulta de um moduleId no curso
	public ResponseEntity<List<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId){
		                                                       
		return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(courseId));
	}
*/	
  }
	@GetMapping("/course/{courseId/module") //tendo um pathvariable para consulta de um moduleId no curso
	public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
		                                                   SpecificationTemplate.ModuleSpec spec ,//passando um dado especifico com o filtro determinado no specification
                                                           @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable){    
		return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
  }
	
	@GetMapping("/course/{courseId}/module/{moduleId}")
	public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId,@PathVariable(value = "moduleId") UUID moduleId){
		Optional<ModuleModel>moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);//pesquisando um moduo dentro de um curso
		if(!moduleModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
		}
		return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
	}
}
