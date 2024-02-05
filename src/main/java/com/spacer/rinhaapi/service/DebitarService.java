package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;

public class DebitarService implements TransaccaoStrategy{

    @Override
    public boolean realizarTransacao(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            return false;
        }
        var saldoAtual = cliente.getSaldo() - valor;

        if (saldoAtual < cliente.getLimite()) {
            return false;
        } else {
            cliente.setSaldo(saldoAtual);
            return true;
        }
    }
}
