package com.spacer.rinhaapi;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.ComparatorData;
import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.model.Transacao;
import com.spacer.rinhaapi.model.TransacaoResponse;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void testarMethodDebitarComInconsistenciaValor() {
        var cliente = new Cliente(1, 1000, 2500);

        assertEquals(2500, cliente.getSaldo());
    }

    @Test
    void testarRecuperacaoExtratoComSaldos() {
        var cliente = new Cliente(2, 1000, 2500);
        var t1 = new Transacao(cliente, 500, "c", "teste 1");

        var t2 = new Transacao(cliente, 300, "d", "teste 2");

        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(t1);
        transacoes.add(t2);

        when(transacaoRepository.findTransacaoByCliente_Id(anyInt())).thenReturn(transacoes);

        Extrato extrato = transacaoService.findTransacoesByClienteId(10);

        assertNotNull(extrato);
        assertEquals(2, extrato.getUltimasTransacoes().size());
        assertEquals("d", extrato.getUltimasTransacoes().get(0).tipo());
    }

    @Test
    void testarOrderDasTransacoes() {
        var t1 = new TransacaoResponse(200, "c", "t1", OffsetDateTime.now());
        var t2 = new TransacaoResponse(50, "d", "t2", OffsetDateTime.now().plusHours(3));
        var t3 = new TransacaoResponse(150, "c", "t3", OffsetDateTime.now().plusHours(1));

        List<TransacaoResponse> transacaoResponses = new ArrayList<>();
        transacaoResponses.add(t1);
        transacaoResponses.add(t2);
        transacaoResponses.add(t3);

        transacaoResponses.sort(new ComparatorData());

        assertNotNull(transacaoResponses);
        assertEquals("t2", transacaoResponses.get(0).descricao());
    }
}
