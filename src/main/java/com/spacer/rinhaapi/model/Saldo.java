package com.spacer.rinhaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Saldo(Integer total, @JsonProperty("data_extrato") OffsetDateTime dataExtrato, Integer limite) {
}
