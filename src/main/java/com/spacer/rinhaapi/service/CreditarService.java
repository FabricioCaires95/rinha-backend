package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;

public class CreditarService implements TransaccaoStrategy{

    @Override
    public boolean realizarTransacao(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            return false;
        }
        cliente.setSaldo(cliente.getSaldo() + valor);
        return true;
    }
}
