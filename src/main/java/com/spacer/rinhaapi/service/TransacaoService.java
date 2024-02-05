package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;

public interface TransacaoService {

    boolean creditar(Cliente cliente, Integer valor);

    boolean debitar(Cliente cliente, Integer valor);

    boolean realizarTransacao(Cliente cliente, Integer valor, String tipoTransacao);
}
