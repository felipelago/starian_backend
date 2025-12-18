package com.starian.backend.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AuditEventListResponse(
        @Schema(description = "ID único do evento", example = "1")
        Long id,

        @Schema(description = "Data e hora do evento", example = "2025-12-18 14:30:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,

        @Schema(description = "Método HTTP", example = "POST")
        String method,

        @Schema(description = "Caminho da requisição", example = "/api/v1/usuarios")
        String path,

        @Schema(description = "Status HTTP da resposta", example = "200")
        Integer status,

        @Schema(description = "Tempo de processamento em milissegundos", example = "150")
        Long durationMs,

        @Schema(description = "ID do usuário que realizou a ação", example = "123", nullable = true)
        String userId,

        @Schema(description = "Endereço IP de origem", example = "192.168.1.100")
        String ip,

        @Schema(description = "User-Agent do cliente", example = "Mozilla/5.0")
        String userAgent,

        @Schema(description = "ID de correlação da requisição", example = "abc-123-xyz", nullable = true)
        String correlationId
) {
}
