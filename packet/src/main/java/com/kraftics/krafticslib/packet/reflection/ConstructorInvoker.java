package com.kraftics.krafticslib.packet.reflection;

public interface ConstructorInvoker<T> {

    T invoke(Object... args);
}
