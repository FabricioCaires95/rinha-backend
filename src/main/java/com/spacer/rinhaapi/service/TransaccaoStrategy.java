package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;

@FunctionalInterface
public interface TransaccaoStrategy {

    boolean realizarTransacao(Cliente cliente, Integer valor);
}
