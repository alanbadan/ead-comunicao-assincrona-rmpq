package com.ead.curso.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.curso.model.CourseModel;
import com.ead.curso.model.UserModel;


public interface CourseService {
    
	//assinatura do metodo para dlete um curso e seus lessons sem usar as anoatacoes cascade.all e  ....
	void delete(CourseModel courseModel);

	CourseModel save(CourseModel courseModel);
	
	Optional<CourseModel> findById(UUID courseId);

	Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable); //retorno da lista no get course
	
	boolean existsByCourseAndUser(UUID courseId, UUID userId);

	void saveSubscriptionUserInCourse(UUID courseId, UUID userId);

	void saveSubscriptionUserInCourseAndSendNotificacao(CourseModel course, UserModel user);
	
	
}
