package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    public boolean creditar(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            return false;
        }
        cliente.setSaldo(cliente.getSaldo() + valor);
        return true;
    }

    public boolean debitar(Cliente cliente, Integer valor) {
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

    @Override
    public boolean realizarTransacao(Cliente cliente, Integer valor, String tipoTransacao) {
        if (tipoTransacao.equalsIgnoreCase("c")) {
            return creditar(cliente, valor);
        } else {
            return debitar(cliente, valor);
        }
    }
}
