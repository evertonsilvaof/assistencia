package com.assistencia.dto;

public class OrdemServicoResponse {
package com.assistencia.dto;

import java.time.LocalDateTime;

    public record OrdemServicoResponse(
            Long id,
            String numeroProtocolo,
            String status,
            LocalDateTime dataAbertura
    ) {}

}
