package com.starian.backend.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados para cadastro/atualização de usuário")
public record UserRegisterRequest(

        @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @Schema(description = "CPF do usuário", example = "12345678900", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O CPF é obrigatório")
        @CPF
        String cpf,

        @Schema(description = "CEP sem hífen", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos, sem hífen")
        String cep,

        @Schema(description = "Logradouro", example = "Rua das Flores", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @Schema(description = "Bairro", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Cidade é obrigatório")
        String cidade,

        @Schema(description = "Estado (UF)", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Estado é obrigatório")
        String estado
) {
}
