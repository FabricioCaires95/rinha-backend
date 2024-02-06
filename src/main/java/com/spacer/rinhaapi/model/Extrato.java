package com.spacer.rinhaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Extrato {

    private Saldo saldo;

    @JsonProperty("ultimas_transacoes")
    private List<TransacaoResponse> ultimasTransacoes;

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public List<TransacaoResponse> getUltimasTransacoes() {
        return ultimasTransacoes;
    }

    public void setUltimasTransacoes(List<TransacaoResponse> ultimasTransacoes) {
        this.ultimasTransacoes = ultimasTransacoes;
    }
}
