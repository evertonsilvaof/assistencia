package com.assistencia.controller;

import com.assistencia.dto.ClienteResponse;
import com.assistencia.dto.CriarClienteRequest;
import com.assistencia.entity.ClienteEntity;
import com.assistencia.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteRepository clientes;

    public ClienteController(ClienteRepository clientes) {
        this.clientes = clientes;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody CriarClienteRequest request) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome(request.nome().trim());
        cliente.setAtivo(request.ativo() == null || request.ativo());

        ClienteEntity salvo = clientes.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(salvo));
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clientes.findAll().stream().map(this::toResponse).toList();
    }

    private ClienteResponse toResponse(ClienteEntity cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.isAtivo());
    }
}
