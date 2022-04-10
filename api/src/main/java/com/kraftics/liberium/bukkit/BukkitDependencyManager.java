package com.kraftics.liberium.bukkit;

import com.kraftics.liberium.component.ComponentContainer;
import com.kraftics.liberium.dependency.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BukkitDependencyManager implements DependencyManager {
    private final InstanceContainer defaultContainer = new DefaultInstanceContainer();
    private final List<InstanceContainer> containers = new ArrayList<>();
    private final DependencyInstantier instantier;

    public BukkitDependencyManager(List<Class<?>> dependencies) {
        this.instantier = new DependencyInstantier(this, dependencies);
        registerContainer(new ComponentContainer());
    }

    @NotNull
    @Override
    public Object createDependency(Class<?> type) throws DependencyException {
        InstanceContainer container = getContainer(type);
        Object instance = container.getInstance(type);
        if (instance != null) return instance;
        instance = instantier.instantiate(type);
        container.setInstance(type, instance);
        return instance;
    }

    @Override
    public Object getDependency(Class<?> type) {
        return getContainer(type).getInstance(type);
    }

    @Override
    public boolean hasDependency(Class<?> type) {
        return getContainer(type).hasInstance(type);
    }

    /*
     * TODO: Find more optimized version?
     */
    @NotNull
    @Override
    public List<Class<?>> getRequiredDependencies(Class<?> type) {
        return List.of(Arrays.stream(type.getConstructors())
                .sorted((a, b) -> Integer.compare(b.getParameterTypes().length, a.getParameterTypes().length))
                .map(Constructor::getParameterTypes)
                .findFirst()
                .orElse(new Class<?>[0])
        );
    }

    @Override
    public boolean registerContainer(InstanceContainer container) {
        if (containers.contains(container)) return false;
        return containers.add(container);
    }

    @NotNull
    @Override
    public Stream<InstanceContainer> getContainers() {
        return containers.stream();
    }

    @NotNull
    @Override
    public InstanceContainer getContainer(Class<?> type) {
        return containers.stream().filter(container -> container.supports(type)).findFirst().orElse(defaultContainer);
    }

    @NotNull
    @Override
    public DependencyInstantier getInstantier() {
        return instantier;
    }
}
