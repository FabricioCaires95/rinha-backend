package com.spacer.rinhaapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Cliente cliente;
    @Column(name = "realizado_em")
    private OffsetDateTime dataRealizacao;
    private String tipo;
    private String descricao;
    private Integer valor;

    public Transacao() {
    }

    public Transacao(Cliente cliente, Integer valor, String tipo, String descricao) {
        this.cliente = cliente;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.dataRealizacao = OffsetDateTime.now();
    }

    public Transacao(Integer valor, String tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.dataRealizacao = OffsetDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public OffsetDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(OffsetDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
