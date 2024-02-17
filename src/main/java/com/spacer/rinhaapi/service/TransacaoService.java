package com.spacer.rinhaapi.service;

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

