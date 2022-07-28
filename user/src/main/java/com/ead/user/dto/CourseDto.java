package com.ead.user.dto;

import java.util.UUID;

import com.ead.user.enums.CourseLevel;
import com.ead.user.enums.CourseStatus;

import lombok.Data;

@Data   //repedindo o dto de course de entrsda(que esta no projeto course)
public class CourseDto {
	
	private UUID courseId;
	private UUID userInstructor;
	private String name;
	private String description;
	private String imagemUrl;
	private CourseLevel courseLevel;
	private CourseStatus courseStatus;
	
	

}
