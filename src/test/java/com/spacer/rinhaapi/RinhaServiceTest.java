package com.spacer.rinhaapi;

import com.spacer.rinhaapi.controller.TransacaoRequest;
import com.spacer.rinhaapi.controller.TransacaoResponse;
import com.spacer.rinhaapi.exception.TransacaoInconsistenteException;
import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.repository.ClienteRepository;
import com.spacer.rinhaapi.repository.TransacaoRepository;
import com.spacer.rinhaapi.service.RinhaService;
import com.spacer.rinhaapi.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RinhaServiceTest {

    @InjectMocks
    private RinhaService rinhaService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoService transaccaoService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarTransacaoCreditoSucesso() {
        var request = new TransacaoRequest(1000, "c", "creditar");
        var cliente = new Cliente(1, 1000, 2500);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        when(transaccaoService.realizarTransacao(any(), anyInt(), anyString())).thenReturn(true);

        TransacaoResponse transacaoResponse = rinhaService.realizarTransacao(1, request);

        assertNotNull(transacaoResponse);

        verify(transaccaoService).realizarTransacao(cliente, request.valor(), request.tipo());
    }

    @Test
    void testCriarTransacaodebitoSucesso() {
        var request = new TransacaoRequest(800, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        when(transaccaoService.realizarTransacao(any(), anyInt(), anyString())).thenReturn(true);

        TransacaoResponse transacaoResponse = rinhaService.realizarTransacao(1, request);

        assertNotNull(transacaoResponse);

        verify(transaccaoService).realizarTransacao(cliente, request.valor(), request.tipo());
    }

    @Test
    void testErroAoDebitarComSaldoAbaixoDoLimite() {
        var request = new TransacaoRequest(1500, "d", "debitar");
        var cliente = new Cliente(1, 1000, 2000);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        assertThatThrownBy(() -> rinhaService.realizarTransacao(1, request))
                .isInstanceOf(TransacaoInconsistenteException.class);

        verify(transaccaoService).realizarTransacao(cliente, request.valor(), request.tipo());
    }
}
