package com.kraftics.liberium.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface OnComponent {

    void onComponent(@NotNull Annotation annotation, @NotNull Object component);
}
