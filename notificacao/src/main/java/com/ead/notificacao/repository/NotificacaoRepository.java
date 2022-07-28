package com.ead.notificacao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.notificacao.enums.NotificacaoStatus;
import com.ead.notificacao.model.NotificacaoModel;

//COMUNICACAO ASSINCRONA VIA COMANDS
public interface NotificacaoRepository  extends JpaRepository<NotificacaoModel, UUID>{

	//metodo cuatomisado para retorno de noptificacao 
	Page<NotificacaoModel> findAllByUserAndNotificacaoStatus(UUID userId, NotificacaoStatus notificacaoStatus, Pageable pageable);
	
	
     Optional<NotificacaoModel> findByNotificacaoIdAndUserId(UUID notificacaoId, UUID userId);
}
