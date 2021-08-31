package com.kraftics.liberium.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@FunctionalInterface
public interface OnMethod {

    void onMethod(@NotNull Annotation annotation, @NotNull Object component, @NotNull Method method);
}
