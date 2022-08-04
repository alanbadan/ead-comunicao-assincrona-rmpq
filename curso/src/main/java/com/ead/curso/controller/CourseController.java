package com.ead.curso.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ead.curso.dto.CourseDto;
import com.ead.curso.model.CourseModel;
import com.ead.curso.services.CourseService;
import com.ead.curso.specification.SpecificationTemplate;
import com.ead.curso.validation.CourseValidator;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseValidator courseValidator; //injação para usar o metodo de validação customisado
	
	
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	@PostMapping                                  //(@RequestBody @Valid CourseDto courseDto) VLAIDACAO COM AS CONSTRAINTS E ANOTACAO 
	public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors){ //VALIDACAO MAIS AVANCADA (SEM ANOTACAO) COM UM A CLASSE E ANOTACAO VALIDATOR
		//chamando o validator para verficação
		courseValidator.validate(courseDto, errors);
		if(errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
		}
		
		var courseModel = new CourseModel();
		//precisa fazer a conver~sao usando ObejctMapper ou BeansUtisl ou uasando um set simples ou metod declarado  como no courseUserModel
		BeanUtils.copyProperties(courseDto, courseModel);
		courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		courseModel.setLasteUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
	//	courseService.save(courseModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
	}
	
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	@DeleteMapping("/{idcourseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId); //verifivcando se ha esse id no banco
		if(!courseModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
		}
		courseService.delete(courseModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Course deleted suceessfuly");
	}
	
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	@PutMapping("/{courseId}")
	public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid CourseDto courseDto) {
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		if(!courseModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not Found");
		}
		var courseModel = new CourseModel();
		BeanUtils.copyProperties(courseDto, courseModel);
		courseModel.setLasteUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel)); // usando o save para salvar a atualizacao
	}
	/*
   }
	@GetMapping api retorno de todos os curso 
	public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec ,//passando um dado especifico com o filtro determinado no specification
			                                               @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){    //passando um retorno de uma lista sem paginacao
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));                                                                         //ResponseEntity<List<CourseModel>> getAllCourses()
	}
*/	
	//API PARA CMOMUNICACAO SINCRONA COM USER 
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	@GetMapping            //ResponseEntity<List<CourseModel>> getAllCourses()
	public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec ,//passando um dado especifico com o filtro determinado no specification
			                                               @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,    //passando um retorno de uma lista sem paginacao
		                                                   @RequestParam(required = false) UUID userId){
		if(userId != null) {
			return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(SpecificationTemplate.couserUserId(userId).and(spec), pageable));
			
		}else {
			
			return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
			
		}
			
	}
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	@GetMapping("/{courseId}") //passando o pathvariable para pesquisa por id do curso
	public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId){ //object pq nao sabe o retorno
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		if(courseModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
	}
}
