package com.ead.curso.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ead.curso.dto.UserEventDto;
import com.ead.curso.enuns.ActionType;
import com.ead.curso.services.UserService;

@Component //classe que escurta o evento publicado
public class UserConsumer {
	
	
	@Autowired
	UserService userService;
	
	 @RabbitListener(bindings = @QueueBinding(
	            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
	            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
	    )
	    public void listenUserEvent(@Payload UserEventDto userEventDto){
	        var userModel = userEventDto.convertToUserModel(); //convertendo o userEventDto para userModel
           //verificando o tipo de acao do evento
	        switch (ActionType.valueOf(userEventDto.getActionType())){ //covertendo string par enum
	            case CREATE:
	            case UPDATE:
	            	userService.save(userModel);
	            	break;
	            case DELETE:
	            	userService.delete(userEventDto.getUserId());
	            	break;
	        }
	    }

}
