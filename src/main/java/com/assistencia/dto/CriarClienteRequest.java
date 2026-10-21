package com.assistencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarClienteRequest(
        @NotBlank(message = "O nome do cliente é obrigatório.")
        @Size(max = 150, message = "O nome do cliente deve ter no máximo 150 caracteres.")
        String nome,
        Boolean ativo
) {}
