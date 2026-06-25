package com.raizes_do_nordeste.api.exception;

public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException(String message) {
        super(message);
    }
}