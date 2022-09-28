package com.kraftics.liberium.reflection.exception;

public class ReflectionInvocationException extends ReflectionException {

    public ReflectionInvocationException() {
    }

    public ReflectionInvocationException(String message) {
        super(message);
    }

    public ReflectionInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionInvocationException(Throwable cause) {
        super(cause);
    }
}
