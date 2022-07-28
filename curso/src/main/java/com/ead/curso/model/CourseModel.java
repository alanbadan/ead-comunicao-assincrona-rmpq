package com.ead.curso.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ManyToAny;

import com.ead.curso.enuns.CourseLevel;
import com.ead.curso.enuns.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_course")
public class CourseModel implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID courseId;
	@Column(nullable = false, length = 150)
	private String name;
	@Column(nullable = false, length = 150)
	private String description;
	@Column 
	private String imageUrl;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
//	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T' HH:mm:ss'Z'") //Padrao iso 8601 UTC
	private LocalDateTime creationDate;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime lasteUpdateDate;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseStatus courseStatus;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING) // anotacao para enuns
	private CourseLevel courseLevel;
	@Column(nullable = false)
	private UUID userInstrutictor;
	
	
	//colecao de module
	//um curso para mutos modulos                        //lazy somente carrega os dasos necessarios
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy = "course",fetch = FetchType.LAZY) // a chave estrangira para module
	@Fetch(FetchMode.SUBSELECT) // ESTUDAR OS CASOS DE CONSULTAS 
	private Set<ModuleModel> module; // Set pq nãp permite duplicada, melohor opcao para cloecoes com associacões

	//comunicacao sincrona multiplos cursos para multiplos usuarios
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_course_user",  //nome da trabela intermediaria (quando um aluno se matricular em um curso gera uma nova linha)
	           joinColumns = @JoinColumn(name = "course_id"),
	           inverseJoinColumns = @JoinColumn(name = "user_id")) // um join com a  coluna userModel
	private Set<UserModel> user;
	
	
	
//	@JoinColumn(name = " nnnnnn") anotacao para definir nome da coluna senão o  hibernat gera o nome "dele"
	
	public UUID getCourseId() {
		return courseId;
	}

	public void setCourseId(UUID coureId) {
		this.courseId = coureId;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getLasteUpdateDate() {
		return lasteUpdateDate;
	}

	public void setLasteUpdateDate(LocalDateTime lasteUpdateDate) {
		this.lasteUpdateDate = lasteUpdateDate;
	}

	public CourseStatus getCoureseStatus() {
		return courseStatus;
	}

	public void setCoureseStatus(CourseStatus coureseStatus) {
		this.courseStatus = coureseStatus;
	}

	public CourseLevel getCourseLevel() {
		return courseLevel;
	}

	public void setCourseLevel(CourseLevel courseLevel) {
		this.courseLevel = courseLevel;
	}

	public UUID getUserInstrutictor() {
		return userInstrutictor;
	}

	public void setUserInstrutictor(UUID userInstrutictor) {
		this.userInstrutictor = userInstrutictor;
	}
	
}
