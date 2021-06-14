package com.kraftics.liberium.packet.reflection;

public interface FieldAccessor<T> {

    T get(Object target);

    void set(Object target, Object value);

    boolean has(Object target);
}
