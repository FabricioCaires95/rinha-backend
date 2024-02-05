package com.spacer.rinhaapi.controller;

import jakarta.validation.constraints.NotNull;

public record TransacaoRequest(@NotNull(message = "this field is required") Integer valor,
                               @NotNull(message = "this field is required") String tipo,
                               @NotNull(message = "this field is required") String descricao) {
}
