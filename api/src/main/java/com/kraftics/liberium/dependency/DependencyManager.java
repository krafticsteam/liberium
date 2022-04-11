package com.kraftics.liberium.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface DependencyManager {

    @NotNull
    Object createDependency(Class<?> type) throws DependencyException;

    @Nullable
    Object getDependency(Class<?> type);

    boolean hasDependency(Class<?> type);

    @NotNull
    List<Class<?>> getRequiredDependencies(Class<?> type);

    boolean registerContainer(InstanceContainer container);

    @NotNull
    Stream<InstanceContainer> getContainers();

    @NotNull
    Optional<InstanceContainer> getContainer(Class<?> type);

    @NotNull
    InstanceContainer getContainerOrDefault(Class<?> type);

    @NotNull
    DependencyInstantier getInstantier();
}
