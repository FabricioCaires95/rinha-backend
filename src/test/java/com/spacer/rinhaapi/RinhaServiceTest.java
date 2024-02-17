package com.spacer.rinhaapi;

import com.spacer.rinhaapi.controller.TransacaoRequest;
import com.spacer.rinhaapi.controller.TransacaoResponse;
import com.spacer.rinhaapi.exception.TransacaoInconsistenteException;
import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.Transacao;
import com.spacer.rinhaapi.repository.ClienteRepository;
import com.spacer.rinhaapi.service.RinhaService;
import com.spacer.rinhaapi.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RinhaServiceTest {

    @InjectMocks
    private RinhaService rinhaService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoService transaccaoService;

    @Captor
    private ArgumentCaptor<Transacao> captor = ArgumentCaptor.forClass(Transacao.class);


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarTransacaoCreditoSucesso() {
        var request = new TransacaoRequest(1000, "c", "creditar");
        var cliente = new Cliente(1, 1000, 2500);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        TransacaoResponse transacaoResponse = rinhaService.realizarTransacao(1, request);

        verify(transaccaoService).salvarTransacao(captor.capture());

        var transacao = captor.getValue();

        assertNotNull(transacaoResponse);
        assertEquals(3500, transacao.getCliente().getSaldo());
        verify(transaccaoService).salvarTransacao(any(Transacao.class));
    }

    @Test
    void testCriarTransacaodebitoSucesso() {
        var request = new TransacaoRequest(800, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        TransacaoResponse transacaoResponse = rinhaService.realizarTransacao(1, request);

        verify(transaccaoService).salvarTransacao(captor.capture());

        var transacao = captor.getValue();

        assertNotNull(transacaoResponse);
        assertEquals(1200, transacao.getCliente().getSaldo());
        verify(transaccaoService).salvarTransacao(any(Transacao.class));
    }

    @Test
    void testErroAoDebitarComSaldoAbaixoDoLimite() {
        var request = new TransacaoRequest(1500, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        assertThatThrownBy(() -> rinhaService.realizarTransacao(1, request))
                .isInstanceOf(TransacaoInconsistenteException.class);
    }

    @Test
    void testErroComValorNegativo() {
        var request = new TransacaoRequest(-500, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        assertThatThrownBy(() -> rinhaService.realizarTransacao(1, request))
                .isInstanceOf(TransacaoInconsistenteException.class);
    }

    @Test
    void testMetodoCreditarSucesso() {
        var request = new TransacaoRequest(1000, "d", "creditar");
        var cliente = new Cliente(1, 1000, 2000);

        int creditar = rinhaService.creditar(cliente, request.valor());

        assertEquals(3000, creditar);
    }

    @Test
    void testMetodoDebitarSucesso() {
        var request = new TransacaoRequest(1000, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        int debitar = rinhaService.debitar(cliente, request.valor());

        assertEquals(1000, debitar);
    }
}
