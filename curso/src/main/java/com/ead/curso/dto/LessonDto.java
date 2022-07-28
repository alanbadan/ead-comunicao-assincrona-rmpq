package com.ead.curso.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LessonDto {
	
	@NotBlank
	private String title;
	private String description;
	@NotBlank
	private String videoUrl;
	
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
