package com.ead.curso.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

//Dto para parmetrizacao de respostas da paginacao(recebendo os atributos que comtemplam a paginacao)
public class ResponsePageDto <T> extends PageImpl<T> {//T significa generico

	// criando um contrutor para para parametrisar a resposta da paginacao
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)  // usando o Json properties para receber os parametros da paginacao ( vc ve na resposta do postman)
	public ResponsePageDto(@JsonProperty("content") List<T> content,
			               @JsonProperty("number") int number, 
			               @JsonProperty("size")  int size,
		                   @JsonProperty("totalElements") Long totalElements,
			               @JsonProperty("pageable") JsonNode pageable,
			               @JsonProperty("last") boolean last,
			               @JsonProperty("totalPages") int totalPages,
			               @JsonProperty("sort") JsonNode sort,
			               @JsonProperty("first") boolean first,
			               @JsonProperty("empty") boolean empty) {
			               
		
		super(content, PageRequest.of(number, size), totalElements);
	}
			
	
	
	//ide gera sozinho esses contrutores
	public ResponsePageDto(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
		
	} 
	
	public ResponsePageDto(List<T> content) {
		super(content);
	}

}
