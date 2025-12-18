package com.starian.backend.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados para cadastro/atualização de usuário")
public record UserRegisterCepAutoRequest(

        @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @Schema(description = "CPF do usuário", example = "12345678900", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números")
        @CPF(message = "CPF inválido")
        String cpf,

        @Schema(description = "CEP sem hífen", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos, sem hífen")
        String cep
) {
}
