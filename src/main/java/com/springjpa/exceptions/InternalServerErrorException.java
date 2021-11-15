package com.springjpa.exceptions;

public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public InternalServerErrorException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
