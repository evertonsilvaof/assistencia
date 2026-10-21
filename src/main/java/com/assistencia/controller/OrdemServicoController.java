package com.assistencia.controller;

import com.assistencia.dto.*;
import com.assistencia.service.OrdemServicoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens")
public class OrdemServicoController {
    private final OrdemServicoService service;
    public OrdemServicoController(OrdemServicoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<OrdemServicoResponse> abrir(@Valid @RequestBody AbrirOrdemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrir(request));
    }
}
