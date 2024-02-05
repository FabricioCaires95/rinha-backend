package com.spacer.rinhaapi;

import com.spacer.rinhaapi.controller.TransacaoRequest;
import com.spacer.rinhaapi.controller.TransacaoResponse;
import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.repository.ClienteRepository;
import com.spacer.rinhaapi.service.CreditarService;
import com.spacer.rinhaapi.service.RinhaService;
import com.spacer.rinhaapi.service.TransacaoContextoStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

class RinhaServiceTest {

    @InjectMocks
    private RinhaService rinhaService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoContextoStrategy transacaoContextoStrategy;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarTransacaoSucesso() {
        var request = new TransacaoRequest(1000, "c", "realizar credito");
        var cliente = new Cliente(1, 1000, 2500);

        Mockito.when(clienteRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(cliente));
        Mockito.when(transacaoContextoStrategy.resolveStrategy(request)).thenReturn(new CreditarService());

        TransacaoResponse transacaoResponse = rinhaService.realizarTransacao(2, request);

        Assertions.assertNotNull(transacaoResponse);
        Assertions.assertEquals(3500, transacaoResponse.saldo());
    }
}
