package com.spacer.rinhaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record TransacaoResponse(Integer valor,
                                String tipo,
                                String descricao,
                                @JsonProperty("realizada_em") OffsetDateTime dataRealizacao) {



}
