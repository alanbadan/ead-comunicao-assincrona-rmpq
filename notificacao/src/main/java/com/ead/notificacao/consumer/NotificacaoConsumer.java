package com.ead.notificacao.consumer;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ead.notificacao.dto.NotificacaoCommandDto;
import com.ead.notificacao.enums.NotificacaoStatus;
import com.ead.notificacao.model.NotificacaoModel;
import com.ead.notificacao.service.NotificacaoService;
//COMUNICACAO ASSINCRONA VIA COMANDS
@Component //para o spring gerenciar o Bean
public class NotificacaoConsumer {  //classe que consime a fila
	
	@Autowired
	NotificacaoService notificacaoService;
	
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${ead.broker.queue.notificacaoCommandQueue.name}", durable = "true"), //nome da fila definido no .yaml ( cria a fila quando subir a aplicacao)
			exchange = @Exchange(value = "${ead.broker.exchange.notificacaoCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
	        key = "${ead.broker.notificacaoCommandKey")                                                            //nos outros exemplos usamos o Fanault mais aconselhavel éo topic
   )
	
	//recebendo o comandDto
    public void listen(@Payload NotificacaoCommandDto notificacaoDto) { //quando recebr  o dto deve-se conveter em NotificacaoModel
    	var notificacaoModel = new NotificacaoModel();
    	BeanUtils.copyProperties(notificacaoModel, notificacaoModel);
		notificacaoModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		notificacaoModel.setNotificacaoStatus(NotificacaoStatus.CREATED);
		notificacaoService.saveNotificacao(notificacaoModel);
    }
   
}
