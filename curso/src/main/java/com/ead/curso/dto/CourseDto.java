package com.ead.curso.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ead.curso.enuns.CourseLevel;
import com.ead.curso.enuns.CourseStatus;

public class CourseDto {
	
	
	@NotNull
	private UUID userInstructor;
	@NotBlank //nem nullo e nem vazio
	private String name;
	@NotBlank
	private String description;
	private String imageUrl;
	@NotNull
	private CourseStatus courseStatus;
	@NotNull
	private CourseLevel courseLevel;
	
	
	
	
	public UUID getUserInstructor() {
		return userInstructor;
	}
	public void setUserInstructor(UUID userInstructor) {
		this.userInstructor = userInstructor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public CourseStatus getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(CourseStatus courseStatus) {
		this.courseStatus = courseStatus;
	}
	public CourseLevel getCourseLevel() {
		return courseLevel;
	}
	public void setCourseLevel(CourseLevel courseLevel) {
		this.courseLevel = courseLevel;
	}
	

}
