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

import com.ead.curso.dto.NotificacaoDto;
import com.ead.curso.model.CourseModel;
import com.ead.curso.model.LessonModel;
import com.ead.curso.model.ModuleModel;
import com.ead.curso.model.UserModel;
import com.ead.curso.publisher.NotificacaoPublisher;
import com.ead.curso.repository.CourseRepository;
import com.ead.curso.repository.LessonRepository;
import com.ead.curso.repository.ModuleRepositroy;
import com.ead.curso.services.CourseService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepositroy moduleRepositroy;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	NotificacaoPublisher notificacaoPublisher;
	

	
	@Transactional // anaotacao para se caso de errado ele volta ao estado natural que estava.
	@Override
	public void delete(CourseModel courseModel) {// metodo para delecao de modulos em um curso sem usar as anotaçoes cascade.all
			
		                                         //recbeno o modulos // e deltando todos relacionamentos jpa
		List<ModuleModel> moduleModelList = moduleRepositroy.findAllModuleIntoCourse(courseModel.getCourseId());
		if(!moduleModelList.isEmpty()) {
			for(ModuleModel module : moduleModelList) { //percorrendo a lista para ver as lesson dos module
				List<LessonModel> lessonModelList = lessonRepository.findAllLessonIntoModule(module.getModuleId());
				 if(!lessonModelList.isEmpty()) {
					 lessonRepository.deleteAll(lessonModelList); //se não passar o parametro lel deleta tudo !!!!!!!!
				 }
			}
			moduleRepositroy.deleteAll(moduleModelList);
		}
		
		courseRepository.deleteCourseUserByCourse(courseModel.getCourseId()); //deletando usuario do curso state transfer //deleção assincrona
		courseRepository.delete(courseModel); //deletando o curso
		
	}

	@Override
	public CourseModel save(CourseModel courseModel) {
		return courseRepository.save(courseModel);
	}

	@Override
	public Optional<CourseModel> findById(UUID courseId) {
	    return courseRepository.findById(courseId);
	}

	@Override
	public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
		return courseRepository.findAll(spec, pageable); // retornando a listagem de cursos
	}

	@Override // não existe esse metodo (então deve-se croia-lo no repository)
	public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
		return courseRepository.existsByCourseAndUser(courseId, userId);
	}
   
	@Transactional // para garantri o rollBack  esse metodo somente salva
	@Override
	public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
		courseRepository.saveCourseUser(courseId, userId);
		
	}
    //esses metodo salva e publica uma mensagem para o broker 
	@Transactional
	@Override
	public void saveSubscriptionUserInCourseAndSendNotificacao(CourseModel course, UserModel user) {
		courseRepository.saveCourseUser(course.getCourseId(), user.getUserId()); //acessando o curso e o usuario
		
		try { //constrindo o notificacaoDto
			var notificacaoDto = new NotificacaoDto();
			notificacaoDto.setTitle("Bem vindo ao Curso : " + course.getName());
			notificacaoDto.setMessage(user.getFullName() + "sua incrição foi realisada com Sucesso !!");
			notificacaoDto.setUserId(user.getUserId());
			notificacaoPublisher.publisherNotificacao(notificacaoDto);
			
		}catch(Exception e) {
//			log.warn("error sending notificacao")
		}
			
		
	}
	
	 
}
