package com.assistencia.dto;

public class AbrirOrdemRequest {

package com.assistencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

    public record AbrirOrdemRequest(

            @NotNull(message = "O clienteId é obrigatório.")
            @Positive(message = "O clienteId deve ser positivo.")
            Long clienteId,

            @NotNull(message = "O equipamentoId é obrigatório.")
            @Positive(message = "O equipamentoId deve ser positivo.")
            Long equipamentoId,

            @NotBlank(message = "A descrição do problema é obrigatória.")
            String descricaoProblema

    ) {}

}
