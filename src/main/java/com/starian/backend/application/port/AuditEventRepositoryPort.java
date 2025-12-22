package com.starian.backend.application.port;

import com.starian.backend.domain.entity.AuditEventEntity;

import java.util.List;

public interface AuditEventRepositoryPort {

    AuditEventEntity save(AuditEventEntity event);

    List<AuditEventEntity> findAll();
}
