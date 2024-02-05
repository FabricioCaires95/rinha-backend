package com.spacer.rinhaapi.service;

import com.spacer.rinhaapi.model.Cliente;
import com.spacer.rinhaapi.model.TipoTransacao;

public interface TransaccaoService {

    boolean creditar(Cliente cliente, Integer valor);

    boolean debitar(Cliente cliente, Integer valor);

    boolean realizarTransacao(Cliente cliente, Integer valor, TipoTransacao tipoTransacao);
}
