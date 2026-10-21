package com.assistencia.controller;

import com.assistencia.dto.CriarEquipamentoRequest;
import com.assistencia.dto.EquipamentoResponse;
import com.assistencia.entity.ClienteEntity;
import com.assistencia.entity.EquipamentoEntity;
import com.assistencia.exception.RecursoNaoEncontradoException;
import com.assistencia.repository.ClienteRepository;
import com.assistencia.repository.EquipamentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoRepository equipamentos;
    private final ClienteRepository clientes;

    public EquipamentoController(EquipamentoRepository equipamentos, ClienteRepository clientes) {
        this.equipamentos = equipamentos;
        this.clientes = clientes;
    }

    @PostMapping
    public ResponseEntity<EquipamentoResponse> criar(@Valid @RequestBody CriarEquipamentoRequest request) {
        ClienteEntity cliente = clientes.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        EquipamentoEntity equipamento = new EquipamentoEntity();
        equipamento.setDescricao(request.descricao().trim());
        equipamento.setCliente(cliente);

        EquipamentoEntity salvo = equipamentos.save(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(salvo));
    }

    @GetMapping
    public List<EquipamentoResponse> listar() {
        return equipamentos.findAll().stream().map(this::toResponse).toList();
    }

    private EquipamentoResponse toResponse(EquipamentoEntity equipamento) {
        return new EquipamentoResponse(
                equipamento.getId(),
                equipamento.getDescricao(),
                equipamento.getCliente().getId()
        );
    }
}
