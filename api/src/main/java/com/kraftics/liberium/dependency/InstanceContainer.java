package com.kraftics.liberium.dependency;

import org.jetbrains.annotations.Nullable;

public interface InstanceContainer {

    @Nullable
    Object getInstance(Class<?> type);

    void setInstance(Class<?> type, Object object);

    boolean hasInstance(Class<?> type);

    boolean supports(Class<?> type);

    default void configureInstatiation(DependencyInstantier.InstantiationBuilder builder) {

    }
}
