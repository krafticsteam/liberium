package com.kraftics.liberium.reflection.exception;

public class ReflectionNotFoundException extends ReflectionException {

    private Throwable ex;

    public ReflectionNotFoundException() {
    }

    public ReflectionNotFoundException(String message) {
        super(message);
    }
}
