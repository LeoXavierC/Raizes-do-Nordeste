package com.raizes_do_nordeste.api.exception;

public class PagamentoDuplicadoException extends RuntimeException {

    public PagamentoDuplicadoException(String message) {
        super(message);
    }
}