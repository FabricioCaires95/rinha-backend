package com.spacer.rinhaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record TransacaoResponse(Integer valor,
                                String tipo,
                                String descricao,
                                @JsonProperty("realizada_em") OffsetDateTime dataRealizacao) implements Comparable<TransacaoResponse> {


    @Override
    public int compareTo(TransacaoResponse o) {
        if (this.dataRealizacao().isAfter(o.dataRealizacao())) {
            return -1;
        }

        if (this.dataRealizacao().isBefore(o.dataRealizacao())) {
            return 1;
        }

        return 0;
    }

}
