package com.starian.backend.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starian.backend.application.dto.response.AuditEventListResponse;
import com.starian.backend.domain.entity.AuditEventEntity;
import com.starian.backend.infrastructure.repository.AuditEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditEventService {

    private final AuditEventRepository auditEventRepository;
    private final ObjectMapper objectMapper;

    public AuditEventService(AuditEventRepository auditEventPortRepository, ObjectMapper objectMapper) {
        this.auditEventRepository = auditEventPortRepository;
        this.objectMapper = objectMapper;
    }

    @Async
    @Transactional
    public void save(AuditEventEntity event) {
        auditEventRepository.save(event);
    }

    public List<AuditEventListResponse> listarEventosAuditoria() {
        List<AuditEventEntity> listEntity = auditEventRepository.findAll();
        return listEntity.stream().map(item -> objectMapper.convertValue(item, AuditEventListResponse.class)).toList();
    }
}
