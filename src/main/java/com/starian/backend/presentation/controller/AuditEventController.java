package com.starian.backend.presentation.controller;

import com.starian.backend.application.dto.response.AuditEventListResponse;
import com.starian.backend.application.service.AuditEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/v1/auditoria",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Validated
@Tag(name = "Auditoria", description = "Consulta de eventos de auditoria do sistema")
public class AuditEventController {

    private final AuditEventService auditService;

    public AuditEventController(AuditEventService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "Listar eventos de auditoria",
            description = "Retorna todos os eventos de auditoria registrados no sistema")
    @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso")
    public ResponseEntity<List<AuditEventListResponse>> listarEventosDeAuditoria() {
        return ResponseEntity.ok().body(auditService.listarEventosAuditoria());
    }
}
