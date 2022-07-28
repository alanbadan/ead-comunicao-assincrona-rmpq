package com.ead.notificacao.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.notificacao.enums.NotificacaoStatus;
import com.ead.notificacao.model.NotificacaoModel;
import com.ead.notificacao.repository.NotificacaoRepository;
import com.ead.notificacao.service.NotificacaoService;
 

//COMUNICACAO ASSINCRONA VIA COMANDS
@Service
public class NotificacaoServiceImpl implements NotificacaoService{

	@Autowired
	NotificacaoRepository notificacaoRepository;

	@Override
	public NotificacaoModel saveNotificacao(NotificacaoModel notificacaoModel) {
	   return notificacaoRepository.save(notificacaoModel); //passando o que vai ser salvo
	}

	@Override
	public Page<NotificacaoModel> findAllNotificacaoByUser(UUID userId, Pageable pageable) {  //essa pesquisa somente retorna as notificaões criadas e não as lidas
		return notificacaoRepository.findAllByUserAndNotificacaoStatus(userId, NotificacaoStatus.CREATED, pageable);
	}

	@Override
	public Optional<NotificacaoModel> findByNotificacaoIdAndUserId(UUID notificacaoId, UUID userId) {
		return notificacaoRepository.findByNotificacaoIdAndUserId(notificacaoId, userId);
	}
	
}
