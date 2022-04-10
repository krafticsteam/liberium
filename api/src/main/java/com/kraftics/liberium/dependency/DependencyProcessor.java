package com.kraftics.liberium.dependency;

import java.util.function.Consumer;
import java.util.function.Function;

public class DependencyProcessor<T> {
    private final Class<T> type;
    private T object;
    private boolean processAnnotations = true;

    public DependencyProcessor(Class<T> type, T object) {
        this.type = type;
        this.object = object;
    }

    public DependencyProcessor<T> processAnnotations(boolean processAnnotations) {
        this.processAnnotations = processAnnotations;
        return this;
    }

    public DependencyProcessor<T> createInstance(Function<Class<T>, T> instanceProcessor) {
        return this;
    }

    public DependencyProcessor<T> after(Consumer<T> runnable) {
        runnable.accept(object);
        return this;
    }

    public DependencyProcessor<T> modifyAfter(Function<T, T> modify) {
        object = modify.apply(object);
        return this;
    }

    public Class<T> getType() {
        return type;
    }

    public T getObject() {
        return object;
    }
}
