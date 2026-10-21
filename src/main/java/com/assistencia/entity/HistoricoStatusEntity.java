package com.assistencia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_status")
public class HistoricoStatusEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServicoEntity ordemServico;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private StatusOS status;
    @Column(nullable = false)
    private String responsavel;
    @Column(nullable = false, length = 1000)
    private String observacao;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    public Long getId() { return id; }
    public OrdemServicoEntity getOrdemServico() { return ordemServico; }
    public void setOrdemServico(OrdemServicoEntity v) { ordemServico = v; }
    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS v) { status = v; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String v) { responsavel = v; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String v) { observacao = v; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime v) { dataHora = v; }
}
