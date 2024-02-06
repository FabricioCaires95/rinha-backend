package com.spacer.rinhaapi.model;

import com.spacer.rinhaapi.model.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private Cliente cliente;
    private OffsetDateTime dataRealizacao;
    private String tipo;
    private String descricao;
    private Integer valor;

    public Transacao() {
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
