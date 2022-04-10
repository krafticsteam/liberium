package com.kraftics.liberium.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DependencyInstantier {
    private final DependencyManager manager;
    private List<Class<?>> dependencies;

    public DependencyInstantier(DependencyManager manager, List<Class<?>> dependencies) {
        this.manager = manager;
        this.dependencies = dependencies;
    }

    /*
     * TODO: Scan in packages
     * TODO: Add warning/error messages
     */
    public void prepareInit() {
        LinkedList<Class<?>> sorted = new LinkedList<>();
        for (Class<?> clazz : dependencies) {
            try {
                sortDependencies(sorted, clazz, null);
            } catch (DependencyPrepareException e) {
                e.printStackTrace();
            }
        }
        dependencies = sorted;
    }

    private void sortDependencies(@NotNull LinkedList<Class<?>> sorted, @NotNull Class<?> clazz, @Nullable Class<?> parentClazz) throws DependencyPrepareException {
        for (Class<?> dependency : manager.getRequiredDependencies(clazz)) {
            if (sorted.contains(dependency)) continue;
            if (dependency.equals(parentClazz)) throw new DependencyPrepareException("Dependency duplicates").addAffectedDependency(clazz);
            try {
                sortDependencies(sorted, dependency, clazz);
            } catch (DependencyPrepareException e) {
                throw e.addAffectedDependency(clazz);
            }
        }
        sorted.add(clazz);
    }

    /*
     * TODO: Add warning messages
     * TODO: Add instantiation events
     */
    @NotNull
    public Object instantiate(Class<?> clazz) throws DependencyException {
        InstanceContainer container = manager.getContainer(clazz);
        if (container.hasInstance(clazz)) throw new DependencyException("Instance already exists");
        Constructor<?> constructor = getValidConstructor(clazz).orElseThrow(() -> new DependencyException("Valid constructor not found for class: " + clazz));
        return instantiateConstructor(constructor);
    }

    @NotNull
    public Object instantiateConstructor(Constructor<?> constructor) throws DependencyException {
        try {
            return constructor.newInstance(Arrays.stream(constructor.getParameterTypes()).map(manager::getDependency).toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DependencyException("Could not instantiate " + constructor.getDeclaringClass() + " using constructor: " + constructor, e);
        }
    }

    @NotNull
    public Optional<Constructor<?>> getValidConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors()).filter(this::isConstructorValid).findFirst();
    }

    /*
     * TODO: Annotation for optional parameters
     */
    public boolean isConstructorValid(Constructor<?> constructor) {
        for (Class<?> parameter : constructor.getParameterTypes()) {
            if (!manager.hasDependency(parameter)) {
                return false;
            }
        }
        return true;
    }
}
