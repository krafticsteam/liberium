package com.kraftics.liberium.packet.reflection;

public interface ConstructorInvoker<T> {

    T invoke(Object... args);
}
