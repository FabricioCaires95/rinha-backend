package com.spacer.rinhaapi.repository;

import com.spacer.rinhaapi.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    List<Transacao> findTransacaoByCliente_Id(Integer clienteId);
}
