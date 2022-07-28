package com.ead.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.curso.model.ModuleModel;

public interface ModuleService {
  
	void delete(ModuleModel moduleModel);

	ModuleModel save(ModuleModel moduleModel);
	
	 Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

//	Optional<ModuleModel> findModuleIntoCourse(Specification<LessonModel> specification, Pageable pageable);

	List<ModuleModel> findAllByCourse(UUID courseId);

	Optional<ModuleModel> findById(UUID moduleId);

	Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);

}
