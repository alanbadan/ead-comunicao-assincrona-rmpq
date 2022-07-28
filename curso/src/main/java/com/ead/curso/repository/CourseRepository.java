package com.ead.curso.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.curso.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel>{

	    //se encontar dados compativeis de courseId e userId ele retorna true  senão retorna false 
	    @Query(value="select case when count(tcu) > 0 THEN true ELSE false END FROM tb_course_user tcu WHERE tcu.course_id= :courseId and tcu.user_id= :userId",nativeQuery = true)
	    boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

	    @Modifying // anotacao pq não é uma consulta e sim um Insert 
	    @Query(value="insert into tb_course_user values (:courseId,:userId);",nativeQuery = true)
	    void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);
	
	    
	    //delete cascade com state transfer pattern( pode usa Anotacao Jpa @cascadetype all ou remove mas deve -se tomar cuidado com esse tipo de abordadegem q se faz muitos comandoa sql)
	    //deleção assincrona
	    @Modifying
	    @Query(value="delete from tb_courses_users where course_id= :courseId",nativeQuery = true)
	    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);

	    @Modifying
	    @Query(value="delete from tb_courses_users where user_id= :userId",nativeQuery = true)
	    void deleteCourseUserByUser(@Param("userId") UUID userId);
	    
}
