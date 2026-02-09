package com.starian.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de Segurança da Aplicação
 * 
 * IMPORTANTE: Esta configuração é apropriada APENAS para desenvolvimento/demonstração.
 * 
 * PROBLEMAS DE SEGURANÇA EXISTENTES:
 * - CSRF está desabilitado: A aplicação está vulnerável a ataques Cross-Site Request Forgery
 * - Todas as requisições são permitidas sem autenticação: Qualquer pessoa pode acessar todos os endpoints
 * - HTTP Basic está habilitado mas não é utilizado efetivamente
 * 
 * RECOMENDAÇÕES PARA PRODUÇÃO:
 * 1. Implementar autenticação JWT ou OAuth2
 * 2. Habilitar CSRF para endpoints que modificam dados
 * 3. Configurar autorização baseada em roles (RBAC)
 * 4. Adicionar rate limiting para prevenir ataques de força bruta
 * 5. Implementar HTTPS obrigatório
 * 6. Adicionar validação de entrada em todos os endpoints
 * 7. Implementar políticas de senha forte
 * 8. Adicionar logs de auditoria para eventos de segurança
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // TODO: Habilitar CSRF em produção
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // TODO: Implementar autenticação real
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}