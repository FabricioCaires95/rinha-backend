package com.spacer.rinhaapi.model;

public enum TipoTransacao {

    CREDITO("c"),
    DEBITO("d");

    private String tipoTransacao;

    TipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
