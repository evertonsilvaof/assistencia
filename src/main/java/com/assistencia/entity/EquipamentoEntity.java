package com.assistencia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipamentos")
public class EquipamentoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }
}
