package com.ead.curso.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.curso.model.LessonModel;
import com.ead.curso.repository.LessonRepository;
import com.ead.curso.services.LessonService;


@Service
public class LessonServiceImpl implements LessonService{
	
	@Autowired
	LessonRepository lessonRepository;

	@Override
	public LessonModel save(LessonModel lessonModel) {
		return lessonRepository.save(lessonModel);
		
	}

	@Override
	public Optional<LessonModel> findLessonIntoModel(UUID moduleId, UUID lessonId) {
		return  lessonRepository.findLessonIntoModel(moduleId, lessonId);
	}

	@Override
	public void delete(LessonModel lessonModel) {
		lessonRepository.delete(lessonModel);
		
	}

	@Override
	public List<LessonModel> findAllByModule(UUID moduleId) {
		return lessonRepository.findAllLessonIntoModule(moduleId); //aproveitando o metodo do repositori para pesquisa de lesson debtor do modulo
	}

	@Override
	public Page<LessonModel> findModuleIntoCourse(Specification<LessonModel> spec, Pageable pageable) {
		return lessonRepository.findAll(spec, pageable);
	}

}
