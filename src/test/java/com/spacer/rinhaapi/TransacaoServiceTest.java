package com.spacer.rinhaapi;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.model.Transacao;
import com.spacer.rinhaapi.repository.TransacaoRepository;
import com.spacer.rinhaapi.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testarMethodoCreditarSucesso() {
        var cliente = new Cliente(1, 1000, 2500);

        boolean result = transacaoService.realizarTransacao(cliente, 1000, "c");

        assertTrue(result);
        assertEquals(3500, cliente.getSaldo());
    }

    @Test
    void testarMethodoCreditarComValorInvalido() {
        var cliente = new Cliente(1, 1000, 2500);

        boolean result = transacaoService.realizarTransacao(cliente, -50, "c");

        assertFalse(result);
        assertEquals(2500, cliente.getSaldo());
    }

    @Test
    void testarMethodDebitarSucesso() {
        var cliente = new Cliente(1, 1000, 2500);

        boolean result = transacaoService.realizarTransacao(cliente, 700, "d");

        assertTrue(result);
        assertEquals(1800, cliente.getSaldo());
    }

    @Test
    void testarMethodDebitarComInconsistenciaValor() {
        var cliente = new Cliente(1, 1000, 2500);

        boolean result = transacaoService.realizarTransacao(cliente, 2000, "d");

        assertFalse(result);
        assertEquals(2500, cliente.getSaldo());
    }

    @Test
    void testarRecuperacaoExtratoComSaldos() {
        var cliente = new Cliente(2, 1000, 2500);
        var t1 = new Transacao();
        t1.setId(2);
        t1.setDescricao("teste 1");
        t1.setTipo("c");
        t1.setValor(500);
        t1.setDataRealizacao(OffsetDateTime.now());
        t1.setCliente(cliente);

        var t2 = new Transacao();
        t2.setId(3);
        t2.setDescricao("teste 2");
        t2.setTipo("d");
        t2.setValor(300);
        t2.setDataRealizacao(OffsetDateTime.now().plusHours(2));
        t2.setCliente(cliente);

        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(t1);
        transacoes.add(t2);

        when(transacaoRepository.findTransacaoByClientId(anyInt())).thenReturn(transacoes);

        Extrato extrato = transacaoService.findTransacoesByClienteId(10);

        assertNotNull(extrato);
        assertEquals(2, extrato.getUltimasTransacoes().size());
    }
}
