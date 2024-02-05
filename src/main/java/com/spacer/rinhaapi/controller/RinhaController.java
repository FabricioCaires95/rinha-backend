package com.spacer.rinhaapi.controller;


import com.spacer.rinhaapi.service.RinhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void getTransactions(Integer id) {
        //
    }
}
