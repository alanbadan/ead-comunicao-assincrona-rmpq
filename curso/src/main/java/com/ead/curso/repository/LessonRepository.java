package com.ead.curso.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.curso.model.LessonModel;

public interface LessonRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel>{

	// estudar @query e @modifing
		@Query(value = "select * from tb_lesson where module_module_id = : moduleId", nativeQuery = true)
		List<LessonModel> findAllLessonIntoModule(@Param("moduleId") UUID coureseId);
	
		//
	   @Query(value = "select * from tb_lesson where module_module_id = :module_id and lesson_id" , nativeQuery = true)
        Optional<LessonModel> findLessonIntoModel(@Param("moduleId") UUID moduleId, @Param("lesson_id") UUID lessonId);
}            // a pesquisa é feirta pelo parametro vindo do metodo ou seja moduleId e lessonId)
