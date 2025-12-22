package com.starian.backend.infrastructure.repository;

import com.starian.backend.application.port.AuditEventRepositoryPort;
import com.starian.backend.domain.entity.AuditEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEventEntity, Long>, AuditEventRepositoryPort {
}
