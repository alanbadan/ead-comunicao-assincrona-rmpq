package com.ead.curso.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_modules")
public class ModuleModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID moduleId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime creationDate;
	
	//cada modulo pertence ao um modulo de curso
	//varios modulos paea um dererminado curso //cahve estrangeira para cursoid
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // anotacao para sempre associar um curso ao um modulo
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private CourseModel course;
    
	//um modulo tem varias licoes
    @OneToMany(mappedBy = "module",fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) 
    private Set<LessonModel> lessons;
    
	
	//lombok não funciona
	public UUID getModuleId() {
		return moduleId;
	}
	public void setModuleId(UUID moduleId) {
		this.moduleId = moduleId;
	}
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
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 public CourseModel getCourse() {
			return course;
	}
	public void setCourse(CourseModel course) {
			this.course = course;
	}
	
}
