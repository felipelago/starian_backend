package com.starian.backend.application.service;

import com.starian.backend.domain.entity.AuditEventEntity;
import com.starian.backend.infrastructure.repository.AuditEventRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditEventService {

    private final AuditEventRepository auditEventRepository;

    public AuditEventService(AuditEventRepository auditEventPortRepository) {
        this.auditEventRepository = auditEventPortRepository;
    }

    @Async
    public void save(AuditEventEntity event) {
        auditEventRepository.save(event);
    }
}
