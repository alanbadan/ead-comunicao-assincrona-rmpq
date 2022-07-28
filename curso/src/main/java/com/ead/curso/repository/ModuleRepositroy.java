package com.ead.curso.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.curso.model.ModuleModel;

public interface ModuleRepositroy extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

	// estudar @query e @modifing
	@Query(value = "select * from tb_module where course_course_id = : courseId", nativeQuery = true)
	List<ModuleModel> findAllModuleIntoCourse(@Param("courseId") UUID courseId);
      
	//consultando um determinado modulo debtro de um detreminado curso
	@Query(value = "select * from tb_module where course_courseId = :courseId and module_id = :moduleid", nativeQuery = true)
	Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId,@Param("moduleId") UUID moduleId);

	
}
