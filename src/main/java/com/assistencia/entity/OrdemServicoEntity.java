package com.assistencia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordens_servico", uniqueConstraints = @UniqueConstraint(name = "uk_os_protocolo", columnNames = "numero_protocolo"))
public class OrdemServicoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_protocolo", nullable = false, unique = true, length = 19)
    private String numeroProtocolo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private EquipamentoEntity equipamento;
    @Column(nullable = false, length = 2000)
    private String descricaoProblema;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private StatusOS status;
    @Column(nullable = false)
    private LocalDateTime dataAbertura;
    public Long getId() { return id; }
    public String getNumeroProtocolo() { return numeroProtocolo; }
    public void setNumeroProtocolo(String v) { numeroProtocolo = v; }
    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity v) { cliente = v; }
    public EquipamentoEntity getEquipamento() { return equipamento; }
    public void setEquipamento(EquipamentoEntity v) { equipamento = v; }
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String v) { descricaoProblema = v; }
    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS v) { status = v; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime v) { dataAbertura = v; }
}
