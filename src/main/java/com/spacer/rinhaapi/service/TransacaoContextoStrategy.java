package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.controller.TransacaoRequest;
import org.springframework.stereotype.Component;

@Component
public class TransacaoContextoStrategy {

    private TransaccaoStrategy transaccaoStrategy;

    public TransaccaoStrategy resolveStrategy(TransacaoRequest transacao) {
        if (transacao.tipo().equalsIgnoreCase("c")) {
            setTransaccaoStrategy(new CreditarService());
        } else {
            setTransaccaoStrategy(new DebitarService());
        }

        return transaccaoStrategy;
    }

    public TransaccaoStrategy getTransaccaoStrategy() {
        return transaccaoStrategy;
    }

    public void setTransaccaoStrategy(TransaccaoStrategy transaccaoStrategy) {
        this.transaccaoStrategy = transaccaoStrategy;
    }
}
