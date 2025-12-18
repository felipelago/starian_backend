package com.starian.backend.presentation.controller;

import com.starian.backend.application.dto.response.AuditEventListResponse;
import com.starian.backend.application.service.AuditEventService;
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
public class AuditEventController {

    private final AuditEventService auditService;

    public AuditEventController(AuditEventService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<AuditEventListResponse>> listarEventosDeAuditoria() {
        return ResponseEntity.ok().body(auditService.listarEventosAuditoria());
    }
}
