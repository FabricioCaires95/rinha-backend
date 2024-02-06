package com.spacer.rinhaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TransacaoInconsistenteException extends RuntimeException {

    public TransacaoInconsistenteException(String message) {
        super(message);
    }
}
