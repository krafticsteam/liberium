package com.kraftics.liberium.packet.reflection;

public interface MethodInvoker<T> {

    T invoke(Object target, Object... args);
}
