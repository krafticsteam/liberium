package com.kraftics.liberium.annotation;

import com.kraftics.liberium.module.Module;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class WrapperAnnotationProcessor implements AnnotationProcessor {
    private final List<Wrapper<?, ?>> list = new ArrayList<>();
    private final List<Module> disabled = new ArrayList<>();

    @Override
    public void runEvents(Object object) {
        Class<?> clazz = object.getClass();

        runEvents(clazz, object);

        for (Field field : clazz.getFields()) {
            runEvents(field, object);
        }

        for (Method method : clazz.getMethods()) {
            runEvents(method, object);
        }

        for (Constructor<?> constructor : clazz.getConstructors()) {
            runEvents(constructor, object);
        }
    }

    @Override
    public void runEvents(Annotation annotation, AnnotatedElement element, Object object) {
        for (Wrapper<?, ?> wrapper : this.list) {
            if (wrapper.annotation == annotation.annotationType() && wrapper.element == element.getClass() && wrapper.isEnabled()) {
                wrapper.listener.castInit(annotation, element, object);
            }
        }
    }

    @Override
    public List<AnnotationListener<?, ?>> getListeners(Module module) {
        List<AnnotationListener<?, ?>> listeners = new ArrayList<>();
        for (Wrapper<?, ?> wrapper : this.list) {
            if (wrapper.module == module) {
                listeners.add(wrapper.listener);
            }
        }
        return listeners;
    }

    @Override
    public List<AnnotationListener<?, ?>> getListeners(Annotation annotation, AnnotatedElement element) {
        List<AnnotationListener<?, ?>> listeners = new ArrayList<>();
        for (Wrapper<?, ?> wrapper : this.list) {
            if (wrapper.annotation == annotation.annotationType() && wrapper.element == element.getClass()) {
                listeners.add(wrapper.listener);
            }
        }
        return listeners;
    }

    @Override
    public List<AnnotationListener<?, ?>> getEnabledListeners(Annotation annotation, AnnotatedElement element) {
        List<AnnotationListener<?, ?>> listeners = new ArrayList<>();
        for (Wrapper<?, ?> wrapper : this.list) {
            if (wrapper.annotation == annotation.annotationType() && wrapper.element == element.getClass() && wrapper.isEnabled()) {
                listeners.add(wrapper.listener);
            }
        }
        return listeners;
    }

    @Override
    public <A extends Annotation, E extends AnnotatedElement> void registerListener(Module module, Class<A> annotation, Class<E> element, AnnotationListener<A, E> listener) {
        this.list.add(new Wrapper<>(module, annotation, element, listener));
    }

    @Override
    public void enableModule(Module module) {
        disabled.remove(module);
    }

    @Override
    public void disableModule(Module module) {
        disabled.add(module);
    }

    @Override
    public boolean isModuleEnabled(Module module) {
        return !disabled.contains(module);
    }

    private class Wrapper<A extends Annotation, E extends AnnotatedElement> {
        private final Module module;
        private final Class<A> annotation;
        private final Class<E> element;
        private final AnnotationListener<A, E> listener;

        public Wrapper(@NotNull Module module, @NotNull Class<A> annotation, @NotNull Class<E> element, @NotNull AnnotationListener<A, E> listener) {
            this.module = module;
            this.annotation = annotation;
            this.element = element;
            this.listener = listener;
        }

        public boolean isEnabled() {
            return isModuleEnabled(module);
        }
    }
}
