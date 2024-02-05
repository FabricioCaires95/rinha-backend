package com.spacer.rinhaapi.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransacaoRequest(@NotNull(message = "this field is required") Integer valor,
                               @NotNull(message = "this field is required") String tipo,
                               @NotNull(message = "this field is required")
                               @Size(min = 1, max = 10, message = "size error") String descricao) {
}
