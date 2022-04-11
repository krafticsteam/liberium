package com.kraftics.liberium.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

public class DependencyInstantier {
    private final DependencyManager manager;
    private List<Class<?>> dependencies;

    public DependencyInstantier(DependencyManager manager, List<Class<?>> dependencies) {
        this.manager = manager;
        this.dependencies = dependencies;
    }

    /*
     * TODO: Scan in packages
     * TODO: Add and log warning/error messages
     */
    public void sortDependencies() {
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
    public Object instantiate(@NotNull Class<?> clazz) throws DependencyException {
        InstanceContainer container = manager.getContainer(clazz).orElseThrow(() -> new DependencyException("Valid container not found for class: " + clazz));
        if (container.hasInstance(clazz)) throw new DependencyException("Instance already exists");

        InstantiationBuilder builder = new InstantiationBuilder();
        container.configureInstatiation(builder);

        Object o = builder.getInstantiator() == null ?
                instantiateConstructor(getValidConstructor(clazz).orElseThrow(() -> new DependencyException("Valid constructor not found for class: " + clazz))) :
                builder.getInstantiator().instantiate(clazz);

        builder.runAfter(o);
        return o;
    }

    @NotNull
    public Object instantiateConstructor(@NotNull Constructor<?> constructor) throws DependencyException {
        try {
            return constructor.newInstance(Arrays.stream(constructor.getParameterTypes()).map(manager::getDependency).toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DependencyException("Could not instantiate " + constructor.getDeclaringClass() + " using constructor: " + constructor, e);
        }
    }

    /*
     * TODO: Sort by amount of parameters
     */
    @NotNull
    public Optional<Constructor<?>> getValidConstructor(@NotNull Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors()).filter(this::isConstructorValid).findFirst();
    }

    /*
     * TODO: Annotation for optional parameters
     */
    public boolean isConstructorValid(@NotNull Constructor<?> constructor) {
        for (Class<?> parameter : constructor.getParameterTypes()) {
            if (!manager.hasDependency(parameter)) {
                return false;
            }
        }
        return true;
    }

    public static class InstantiationBuilder {
        private final Set<Consumer<Object>> afterConsumers = new HashSet<>();
        private Instantiator instantiator = null;
        private boolean processAnnotations = true;

        @NotNull
        public InstantiationBuilder processAnnotation(boolean processAnnotations) {
            this.processAnnotations = processAnnotations;
            return this;
        }

        @NotNull
        public InstantiationBuilder after(@NotNull Consumer<Object> consumer) {
            afterConsumers.add(consumer);
            return this;
        }

        @NotNull
        public InstantiationBuilder instantiator(@Nullable Instantiator instantiator) {
            this.instantiator = instantiator;
            return this;
        }

        @NotNull
        public Set<Consumer<Object>> getAfterConsumers() {
            return Set.copyOf(afterConsumers);
        }

        @Nullable
        public Instantiator getInstantiator() {
            return instantiator;
        }

        public boolean getProcessAnnotations() {
            return processAnnotations;
        }

        private void runAfter(Object o) {
            afterConsumers.forEach(consumer -> consumer.accept(o));
        }
    }

    @FunctionalInterface
    public interface Instantiator {

        @NotNull
        Object instantiate(@NotNull Class<?> clazz) throws DependencyException;
    }
}
