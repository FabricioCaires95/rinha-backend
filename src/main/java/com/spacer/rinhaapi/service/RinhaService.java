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
        final var cliente =  getClienteById(clientId);

        int newSaldo;

        if (transacao.tipo().equalsIgnoreCase("c")) {
            newSaldo = creditar(cliente, transacao.valor());
        } else if (transacao.tipo().equalsIgnoreCase("d")) {
            newSaldo = debitar(cliente, transacao.valor());
        } else {
            throw new TransacaoInconsistenteException("Tipo de transação inválido");
        }

        var clienteAtualizado = new Cliente(cliente.getId(), cliente.getLimite(), newSaldo);
        clienteRepository.save(clienteAtualizado);
        transacaoService.salvarTransacao(transacao.toTransacao(transacao, clienteAtualizado));
        return new TransacaoResponse(clienteAtualizado.getLimite(), clienteAtualizado.getSaldo());
    }

    public Extrato getTransacoesByClientId(Integer clientId) {
        final var cliente = getClienteById(clientId);

        Extrato extrato = transacaoService.findTransacoesByClienteId(clientId);
        Saldo saldo = new Saldo(cliente.getSaldo(), OffsetDateTime.now(), cliente.getLimite());
        extrato.setSaldo(saldo);

        return extrato;
    }

    public int creditar(Cliente cliente, Integer valor) {
        validarValor(valor);
        return cliente.getSaldo() + valor;
    }

    public int debitar(Cliente cliente, Integer valor) {
        validarValor(valor);

        var saldoFinal = cliente.getSaldo() - valor;

        if (saldoFinal < cliente.getLimite()) {
            throw new TransacaoInconsistenteException("Saldo insuficiente");
        } else {
            return saldoFinal;
        }
    }

    private static void validarValor(Integer valor) {
        if (valor < 0 ) {
            throw new TransacaoInconsistenteException("Valor negativo");
        }
    }

}
