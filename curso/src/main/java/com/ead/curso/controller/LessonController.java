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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.curso.dto.LessonDto;
import com.ead.curso.model.LessonModel;
import com.ead.curso.model.ModuleModel;
import com.ead.curso.services.LessonService;
import com.ead.curso.services.ModuleService;
import com.ead.curso.specification.SpecificationTemplate;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	ModuleService moduleService;
	
	
	@PostMapping("/module/{moduleId}/lesson")
	public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId, @RequestBody @Valid LessonDto lessonDto){
		
		Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId); //buscand o moduloId do banco
		if(!moduleModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not Found");
		}
		var lessonModel = new LessonModel();
		//precisa fazer a conver~sao usando ObejctMapper ou BeansUtisl ou uasando um set simples ou metod declarado  como no courseUserModel
		BeanUtils.copyProperties(lessonDto, lessonModel);
		lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		lessonModel.setModule(moduleModelOptional.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
	}
	@DeleteMapping("/module/{modumeId}/lesson/{lessonId")
	public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId){
		
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModel(moduleId, lessonId);
		if(!lessonModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this course");
		}
		lessonService.delete(lessonModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted with successfuly");
	}
	
     @PutMapping("/module/{modumeId}/lesson/{lessonId")
     public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,@PathVariable(value = "lessonId") UUID lessonId,
                                                 @RequestBody @Valid LessonDto lessonDto){
    	 
    	 Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModel(moduleId, moduleId);
    	 if(!lessonModelOptional.isPresent()){
    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this Module");
    	 }
    	 //conversao dos dados recebidos no dto 
    	 var lessonModel = lessonModelOptional.get();
    	 lessonModel.setTitle(lessonDto.getTitle());
    	 lessonModel.setDescription(lessonDto.getDescription());
    	 lessonModel.setVideoUrl(lessonDto.getVideoUrl());
  //  	 lessonService.save(lessonModel);
    	 
    	 return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));//retirnando o lesson covertido do dto
    	 
     }
     /*
     @GetMapping("/module/{moduleId}/lesson")
      public ResponseEntity<List<LessonModel>> getAllLesson(@PathVariable(value = "moduleId") UUID moduleId) {
    	return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(moduleId));
     }
     */
     @GetMapping("/module/{moduleId/lesson")
 	 public ResponseEntity<Page<LessonModel>> getAllLesson(@PathVariable(value = "moduleId") UUID moduleId,
 		                                                   SpecificationTemplate.LessonSpec spec ,//passando um dado especifico com o filtro determinado no specification
                                                            @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable){    
 		return ResponseEntity.status(HttpStatus.OK).body(lessonService.findModuleIntoCourse(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
 		
     }
     @GetMapping("/module/{modumeId}/lesson/{lessonId")
     public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId){
    	 
    	 Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModel(moduleId, lessonId);
    	 if(!lessonModelOptional.isPresent()) {
    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this Module");
    	 }
    	 return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
     }

}
