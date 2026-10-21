package com.assistencia.service;

import com.assistencia.dto.AbrirOrdemRequest;
import com.assistencia.dto.OrdemServicoResponse;
import com.assistencia.entity.*;
import com.assistencia.exception.*;
import com.assistencia.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class OrdemServicoService {
    private final ClienteRepository clientes;
    private final EquipamentoRepository equipamentos;
    private final OrdemServicoRepository ordens;
    private final HistoricoStatusRepository historicos;

    public OrdemServicoService(ClienteRepository clientes, EquipamentoRepository equipamentos,
            OrdemServicoRepository ordens, HistoricoStatusRepository historicos) {
        this.clientes = clientes; this.equipamentos = equipamentos;
        this.ordens = ordens; this.historicos = historicos;
    }

    @Transactional
    public OrdemServicoResponse abrir(AbrirOrdemRequest request) {
        ClienteEntity cliente = clientes.findById(request.clienteId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
        EquipamentoEntity equipamento = equipamentos.findById(request.equipamentoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado."));

        if (!cliente.isAtivo())
            throw new RegraNegocioException("Não é permitido abrir Ordem de Serviço para um cliente inativo.");
        if (!equipamento.getCliente().getId().equals(cliente.getId()))
            throw new RegraNegocioException("O equipamento informado não pertence ao cliente indicado.");

        LocalDateTime agora = LocalDateTime.now();
        OrdemServicoEntity ordem = new OrdemServicoEntity();
        ordem.setCliente(cliente);
        ordem.setEquipamento(equipamento);
        ordem.setDescricaoProblema(request.descricaoProblema().trim());
        ordem.setStatus(StatusOS.RECEBIDA);
        ordem.setDataAbertura(agora);
        ordem.setNumeroProtocolo(gerarProtocolo(agora));
        ordem = ordens.saveAndFlush(ordem);

        HistoricoStatusEntity historico = new HistoricoStatusEntity();
        historico.setOrdemServico(ordem);
        historico.setStatus(StatusOS.RECEBIDA);
        historico.setResponsavel("SISTEMA");
        historico.setObservacao("Abertura da Ordem de Serviço.");
        historico.setDataHora(agora);
        historicos.save(historico);

        return new OrdemServicoResponse(ordem.getId(), ordem.getNumeroProtocolo(),
                ordem.getStatus().name(), ordem.getDataAbertura());
    }

    private String gerarProtocolo(LocalDateTime data) {
        String dia = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String codigo;
        do {
            codigo = UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase();
        } while (ordens.existsByNumeroProtocolo("OS-" + dia + "-" + codigo));
        return "OS-" + dia + "-" + codigo;
    }
}
