package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.controller.TransacaoRequest;
import com.spacer.rinhaapi.controller.TransacaoResponse;
import com.spacer.rinhaapi.exception.NotFoundException;
import com.spacer.rinhaapi.exception.TransacaoInconsistenteException;
import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.model.Saldo;
import com.spacer.rinhaapi.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class RinhaService {

    private final ClienteRepository clienteRepository;
    private final TransacaoService transacaoService;

    public RinhaService(ClienteRepository clienteRepository, TransacaoService transacaoService) {
        this.clienteRepository = clienteRepository;
        this.transacaoService = transacaoService;
    }

    public Cliente getClienteById(Integer id) {
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente nao existe"));
    }


    @Transactional(rollbackOn = Exception.class)
    public TransacaoResponse realizarTransacao(Integer clientId, TransacaoRequest transacao) {
        var cliente =  getClienteById(clientId);

        boolean resultTransacao = transacaoService.realizarTransacao(cliente, transacao.valor(), transacao.tipo());

        if (resultTransacao) {
            clienteRepository.save(cliente);
            transacaoService.salvarTransacao(cliente, transacao);
            return new TransacaoResponse(cliente.getLimite(), cliente.getSaldo());
        } else {
            throw new TransacaoInconsistenteException("Não foi possivel realizar a operação");
        }
    }

    public Extrato getTransacoesByClientId(Integer clientId) {
        var cliente = getClienteById(clientId);

        Extrato extrato = transacaoService.findTransacoesByClienteId(clientId);
        Saldo saldo = new Saldo(cliente.getSaldo(), OffsetDateTime.now(), cliente.getSaldo());
        extrato.setSaldo(saldo);

        return extrato;
    }

}
