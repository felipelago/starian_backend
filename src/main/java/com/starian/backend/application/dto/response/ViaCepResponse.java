package com.starian.backend.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCepResponse(
        String logradouro,
        String bairro,
        @JsonProperty("localidade")
        String cidade,
        String estado,
        Boolean erro
) {
}
