package com.spacer.rinhaapi.controller;


import com.spacer.rinhaapi.service.RinhaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class RinhaController {

    private final RinhaService rinhaService;

    public RinhaController(RinhaService rinhaService) {
        this.rinhaService = rinhaService;
    }

    @PostMapping("/{id}/transacoes")
    public void createTransaction(Integer id, @RequestBody TransacaoRequest transacaoRequest) {
        //
    }

    @GetMapping("/{id}/extrato")
    public void getTransactions(Integer id) {
        //
    }
}
