package com.kraftics.liberium.dependency;

public class DependencyException extends Exception {

    public DependencyException(String message) {
        super(message);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyException(Throwable cause) {
        super(cause);
    }
}
