package com.kraftics.krafticslib.packet.reflection;

public interface MethodInvoker<T> {

    T invoke(Object target, Object... args);
}
