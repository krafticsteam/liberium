package com.kraftics.liberium.dependency;

import java.util.HashSet;
import java.util.Set;

public class DependencyPrepareException extends DependencyException {
    private final Set<Class<?>> affectedDependencies = new HashSet<>();

    public DependencyPrepareException(String message) {
        super(message);
    }

    public DependencyPrepareException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyPrepareException(Throwable cause) {
        super(cause);
    }

    public DependencyPrepareException addAffectedDependency(Class<?> dependency) {
        affectedDependencies.add(dependency);
        return this;
    }

    @Override
    public String getMessage() {
        return affectedDependencies.isEmpty() ? super.getMessage() : (super.getMessage() + "\nAffected: " + affectedDependencies);
    }

    @Override
    public String getLocalizedMessage() {
        return this.getMessage();
    }
}
