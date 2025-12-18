package com.starian.backend.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserListResponse(
        Long id,
        String nome,
        String cpf,
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String estado
) {
}
