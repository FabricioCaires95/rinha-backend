package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.ComparatorData;
import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.model.Transacao;
import com.spacer.rinhaapi.model.TransacaoResponse;
import com.spacer.rinhaapi.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

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


    public boolean realizarTransacao(Cliente cliente, Integer valor, String tipoTransacao) {
        if (tipoTransacao.equalsIgnoreCase("c")) {
            return creditar(cliente, valor);
        } else {
            return debitar(cliente, valor);
        }
    }


    public Extrato findTransacoesByClienteId(Integer clienteId) {
        var transacoes = transacaoRepository.findTransacaoByClientId(clienteId)
                .stream()
                .map(this::fromTransacaoToResponse)
                .sorted(new ComparatorData())
                .toList();

        Extrato extrato = new Extrato();
        extrato.setUltimasTransacoes(transacoes);

        return extrato;
    }

    private TransacaoResponse fromTransacaoToResponse(Transacao transacao) {
        return new TransacaoResponse(transacao.getValor(), transacao.getTipo(), transacao.getDescricao(), transacao.getDataRealizacao());
    }


}

