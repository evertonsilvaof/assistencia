package com.assistencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CriarEquipamentoRequest(
        @NotBlank(message = "A descrição do equipamento é obrigatória.")
        @Size(max = 200, message = "A descrição do equipamento deve ter no máximo 200 caracteres.")
        String descricao,
        @NotNull(message = "O clienteId é obrigatório.")
        @Positive(message = "O clienteId deve ser positivo.")
        Long clienteId
) {}
