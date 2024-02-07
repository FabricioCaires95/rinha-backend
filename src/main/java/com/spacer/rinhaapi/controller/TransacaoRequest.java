package com.spacer.rinhaapi.controller;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.Transacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransacaoRequest(@NotNull(message = "this field is required") Integer valor,
                               @NotNull(message = "this field is required") String tipo,
                               @NotNull(message = "this field is required")
                               @Size(min = 1, max = 10, message = "size error") String descricao) {
    public Transacao toTransacao(TransacaoRequest transacaoRequest, Cliente cliente) {
        return new Transacao(cliente,transacaoRequest.valor, transacaoRequest.tipo, transacaoRequest.descricao);
    }
}
