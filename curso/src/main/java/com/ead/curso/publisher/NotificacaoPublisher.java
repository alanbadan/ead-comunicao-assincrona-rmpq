package com.ead.curso.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ead.curso.dto.NotificacaoDto;

@Component
public class NotificacaoPublisher { //classe para plublicar a notificacao 
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value(value = "${ead.broker.exchange.notificacaoCommandExchange}")
	private String notificacaoCommandExchange; //string para assesar os vsalores no .ymal
	
	@Value(value = "${ead.broker.key.notificacaoCommandKey}")
	private String notificacaoCommandKey;
	
	 //metdo para envio da notificacao
	public void publisherNotificacao(NotificacaoDto notificacaoDto) {
		rabbitTemplate.convertAndSend(notificacaoCommandExchange, notificacaoCommandKey, notificacaoDto);
	}                               //nome da msg para onde vai ser enviado , a rotingKey para onde vai a msg, eo corpo da msg 
	

}
