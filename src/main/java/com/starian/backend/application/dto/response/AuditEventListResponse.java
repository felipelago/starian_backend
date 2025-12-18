package com.starian.backend.application.dto.response;

import java.time.LocalDateTime;

public record AuditEventListResponse(
        Long id,
        LocalDateTime timestamp,
        String method,
        String path,
        Integer status,
        Long durationMs,
        String userId,
        String ip,
        String userAgent,
        String correlationId
) {
}
