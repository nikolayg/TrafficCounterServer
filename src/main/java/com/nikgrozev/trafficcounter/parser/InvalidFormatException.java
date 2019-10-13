package com.nikgrozev.trafficcounter.parser;

public class InvalidFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidFormatException(final String message) {
        super(message);
    }
}
