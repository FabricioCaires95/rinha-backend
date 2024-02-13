package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.model.Transacao;
import com.spacer.rinhaapi.model.TransacaoResponse;
import com.spacer.rinhaapi.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    synchronized public boolean creditar(Cliente cliente, Integer valor) {
        if (valor < 0 ) {
            return false;
        }
        cliente.setSaldo(cliente.getSaldo() + valor);
        return true;
    }

    synchronized public boolean debitar(Cliente cliente, Integer valor) {
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


    public boolean debitarCreditar(Cliente cliente, Integer valor, String tipoTransacao) {
        if (tipoTransacao.equalsIgnoreCase("c")) {
            return creditar(cliente, valor);
        } else if (tipoTransacao.equalsIgnoreCase("d")) {
            return debitar(cliente, valor);
        } else {
            return false;
        }
    }


    public Extrato findTransacoesByClienteId(Integer clienteId) {
        var transacoes = transacaoRepository.findTransacaoByCliente_Id(clienteId)
                .stream()
                .map(this::fromTransacaoToResponse)
                .sorted(TransacaoResponse::compareTo)
                .limit(10)
                .toList();

        Extrato extrato = new Extrato();
        extrato.setUltimasTransacoes(transacoes);

        return extrato;
    }

    private TransacaoResponse fromTransacaoToResponse(Transacao transacao) {
        return new TransacaoResponse(transacao.getValor(), transacao.getTipo(), transacao.getDescricao(), transacao.getDataRealizacao());
    }

    public void salvarTransacao(Transacao transacao) {
        transacaoRepository.save(transacao);
    }


}

