package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.exception.TransacaoInconsistenteException;
import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.TipoTransacao;
import org.springframework.stereotype.Service;

@Service
public class TransacaoServiceImpl implements TransaccaoService {

    public boolean creditar(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            throw new TransacaoInconsistenteException("");
        }
        cliente.setSaldo(cliente.getSaldo() + valor);
        return true;
    }

    public boolean debitar(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            throw new TransacaoInconsistenteException("");
        }
        var saldoAtual = cliente.getSaldo() - valor;

        if (saldoAtual < cliente.getLimite()) {
            throw new TransacaoInconsistenteException("");
        } else {
            cliente.setSaldo(saldoAtual);
            return true;
        }
    }

    @Override
    public boolean realizarTransacao(Cliente cliente, Integer valor, TipoTransacao tipoTransacao) {
        return false;
    }
}
