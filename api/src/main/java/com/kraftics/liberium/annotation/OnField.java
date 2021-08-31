package com.kraftics.liberium.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@FunctionalInterface
public interface OnField {

    void onField(@NotNull Annotation annotation, @NotNull Object component, @NotNull Field field);
}
