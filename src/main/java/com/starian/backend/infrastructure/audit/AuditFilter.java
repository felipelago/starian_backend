package com.starian.backend.infrastructure.audit;

import com.starian.backend.application.service.AuditEventService;
import com.starian.backend.domain.entity.AuditEventEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuditFilter extends OncePerRequestFilter {

    private static final String HEADER_CORRELATION_ID = "X-Correlation-Id";

    private final AuditEventService auditService;

    public AuditFilter(AuditEventService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        Instant timestamp = Instant.now();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - start;

            String method = request.getMethod();
            String path = request.getRequestURI();
            int status = response.getStatus();

            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            String correlationId = request.getHeader(HEADER_CORRELATION_ID);

            String userId = resolveUserId();

            AuditEventEntity event = new AuditEventEntity(
                    timestamp,
                    method,
                    path,
                    status,
                    durationMs,
                    userId,
                    ip,
                    userAgent,
                    correlationId
            );

            auditService.save(event);
        }
    }

    private String resolveUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        return auth.getName();
    }
}