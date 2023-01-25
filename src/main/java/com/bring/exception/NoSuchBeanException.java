package com.bring.exception;

public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException(String errorMessage) {
        super(errorMessage);
    }
}
