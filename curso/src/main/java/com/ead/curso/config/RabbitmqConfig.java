package com.ead.curso.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitmqConfig {

	@Autowired // ponto de injecao para a url do ampq que sta no .ymal
	CachingConnectionFactory cachingConnectionFactory;
	
	
	@Bean //metodo para publisher do rmpq
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template =  new RabbitTemplate(cachingConnectionFactory); //passando como vai ser a conexão 
		template.setMessageConverter(messageConverter());
		return template;
	}
	 
	//metod para serializacao dos dados para o broker principalmente datas
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(objectMapper);
	}
	
}
