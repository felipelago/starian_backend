package com.starian.backend.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starian.backend.application.dto.response.AuditEventListResponse;
import com.starian.backend.application.port.AuditEventRepositoryPort;
import com.starian.backend.domain.entity.AuditEventEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditEventService {
    private static final Logger logger = LoggerFactory.getLogger(AuditEventService.class);

    private final AuditEventRepositoryPort auditEventRepository;
    private final ObjectMapper objectMapper;

    public AuditEventService(AuditEventRepositoryPort auditEventRepository, ObjectMapper objectMapper) {
        this.auditEventRepository = auditEventRepository;
        this.objectMapper = objectMapper;
    }

    @Async
    @Transactional
    public void save(AuditEventEntity event) {
        logger.debug("Salvando evento de auditoria assíncrono: {} {}", event.getMethod(), event.getPath());
        auditEventRepository.save(event);
    }

    public List<AuditEventListResponse> listarEventosAuditoria() {
        logger.debug("Listando eventos de auditoria");
        List<AuditEventEntity> listEntity = auditEventRepository.findAll();
        return listEntity.stream().map(item -> objectMapper.convertValue(item, AuditEventListResponse.class)).toList();
    }
}
