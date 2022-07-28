package com.ead.curso.specification;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ead.curso.model.CourseModel;
import com.ead.curso.model.LessonModel;
import com.ead.curso.model.ModuleModel;
import com.ead.curso.model.UserModel;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {
	
	@And({             
	     @Spec(path ="courseLevel", spec = Equal.class),  //sempre usar Equals para enuns
	     @Spec(path ="courseStatus", spec = Equal.class),
	     @Spec(path ="name", spec = Like.class) 
	})
	public interface CourseSpec extends Specification<CourseModel>{} // sepcfication do srpingJpa
	
//spec para user comunicacao soncrona spec de userModel
	@And({             
	     @Spec(path ="email", spec = Like.class),  //sempre usar Equals para enuns
	     @Spec(path ="fullName", spec = Like.class),
	     @Spec(path ="userStatus", spec = Equal.class),
	     @Spec(path ="usertype", spec = Equal.class)})
	public interface UserSpec extends Specification<UserModel>{}
	
		
	@Spec(path = "title", spec = Like.class)	
    public interface ModuleSpec extends Specification<ModuleModel>{}
	
	@Spec(path = "title", spec = Like.class)
	public interface LessonSpec extends Specification<LessonModel>{}
	
	
	
	//aula filtros avançados com apis
	//jpa Criteria Builder
	//realcao modulo curso
	//metdod para combinacao de consulta pelo spec um determinado ID com os filros do specification
	 public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {//pesquisando um lista de modulos determinado pelo cursoiD 
	        return (root, query, cb) -> {
	            query.distinct(true);//definicao sem valores duplicados
	            Root<ModuleModel> module = root;
	            Root<CourseModel> course = query.from(CourseModel.class);
	            Expression<Collection<ModuleModel>> courseModules = course.get("module");
	            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, courseModules));
	        };
	    }
	 //relacaso lesson module
	 public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {//pesquisando um lista de modulos determinado pelo cursoiD 
	        return (root, query, cb) -> {
	            query.distinct(true);//definicao sem valores duplicados
	            Root<LessonModel> lesson = root;
	            Root<ModuleModel> module = query.from(ModuleModel.class);
	            Expression<Collection<LessonModel>> moduleLesson = module.get("lesson");
	            return cb.and(cb.equal(module.get("courseId"), moduleId), cb.isMember(lesson, moduleLesson));
	        };
	    }
	//  comunicacao sincrona userModel manytomany courseModel 
	 public static Specification<UserModel> userCourseId(final UUID courseId) {//pesquisando um lista de modulos determinado pelo cursoiD 
	        return (root, query, cb) -> {
	            query.distinct(true);//definicao sem valores duplicados
	            Root<UserModel> user = root;
	            Root<CourseModel> course = query.from(CourseModel.class);
	            Expression<Collection<UserModel>> courseUser = course.get("user");
	            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, courseUser));
	 
	        };
	    } 
	 
	//  comunicacao sincrona courseModel manytomany userModel 
		 public static Specification<CourseModel> couserUserId(final UUID userId) {//pesquisando um lista de modulos determinado pelo cursoiD 
		        return (root, query, cb) -> {
		            query.distinct(true);//definicao sem valores duplicados
		            Root<CourseModel> course = root;
		            Root<UserModel> user = query.from(UserModel.class);
		            Expression<Collection<CourseModel>> userCourse = user.get("course");
		            return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(course, userCourse));
		 
		        };
		    } 
	 
}

/*
  public static Specification<EntityA> moduleCourseId(final UUID id) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<EntityA> a = root;
            Root<EntityB> b = query.from(EntityB.class);
            Expression<Collection<EntityA>> colecttionBA = b.get("collection");
            return cb.and(cb.equal(b.get("id"), id), cb.isMember(a, colecttionBA));
        };
    }
 */
  
