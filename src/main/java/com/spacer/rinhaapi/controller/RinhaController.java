package com.spacer.rinhaapi.controller;


import com.spacer.rinhaapi.model.Extrato;
import com.spacer.rinhaapi.service.RinhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class RinhaController {

    private final RinhaService rinhaService;

    public RinhaController(RinhaService rinhaService) {
        this.rinhaService = rinhaService;
    }

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<TransacaoResponse> createTransaction(Integer id, @RequestBody TransacaoRequest transacaoRequest) {
        return ResponseEntity.ok(rinhaService.realizarTransacao(id, transacaoRequest));
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<Extrato> getTransactions(Integer id) {
        return ResponseEntity.ok(rinhaService.getTransacoesByClientId(id));
    }
}
