package com.ead.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.curso.model.LessonModel;

public interface LessonService {

	LessonModel save(LessonModel lessonModel);
	 //merodo para buscar um determonada licao dntro de um modulo de aulas (busca prsolnaliasda no repository
	Optional<LessonModel> findLessonIntoModel(UUID moduleId, UUID lessonId);
	
	void delete(LessonModel lessonModel);
	
	List<LessonModel> findAllByModule(UUID moduleId);
	
	Page<LessonModel> findModuleIntoCourse(Specification<LessonModel> spec, Pageable pageable);

}
