package com.ead.curso.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.curso.model.LessonModel;
import com.ead.curso.model.ModuleModel;
import com.ead.curso.repository.LessonRepository;
import com.ead.curso.repository.ModuleRepositroy;
import com.ead.curso.services.ModuleService;


@Service
public class ModuleServiceImpl implements ModuleService{
	
	@Autowired
	ModuleRepositroy moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
    
	
	@Transactional
	@Override  /// metodo para delecao sem usar a customiaszao Cascade.All ( somente para ter uma maior controle)
	public void delete(ModuleModel moduleModel) {
		List<LessonModel> lessonModelList = lessonRepository.findAllLessonIntoModule(moduleModel.getModuleId());
		 if(!lessonModelList.isEmpty()) {
			 lessonRepository.deleteAll(lessonModelList);
		}
		 moduleRepository.delete(moduleModel);
	}


	@Override
	public ModuleModel save(ModuleModel moduleModel) {
		return moduleRepository.save(moduleModel);
	}


	@Override
	public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
			return moduleRepository.findModuleIntoCourse(courseId, moduleId);
	}


	@Override
	public List<ModuleModel> findAllByCourse(UUID courseId) {
		return moduleRepository.findAllModuleIntoCourse(courseId);
	}


    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }


	@Override
	public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
		return moduleRepository.findAll(spec, pageable);
	}


}
