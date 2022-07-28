package com.ead.notificacao.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ead.notificacao.model.NotificacaoModel;

//COMUNICACAO ASSINCRONA VIA COMANDS
public interface NotificacaoService {

	NotificacaoModel saveNotificacao(NotificacaoModel notificacaoModel);

	Page<NotificacaoModel> findAllNotificacaoByUser(UUID userId, Pageable pageable);

	Optional<NotificacaoModel> findByNotificacaoIdAndUserId(UUID notificacaoId, UUID userId);

}
