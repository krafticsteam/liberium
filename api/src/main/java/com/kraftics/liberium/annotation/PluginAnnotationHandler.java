package com.kraftics.liberium.annotation;

import com.kraftics.liberium.LiberiumPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginAnnotationHandler implements AnnotationHandler {
    private final LiberiumPlugin plugin;
    private final Map<Class<? extends Annotation>, AnnotationEvents> registry = new HashMap<>();

    public PluginAnnotationHandler(@NotNull LiberiumPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handleComponent(Object componentObject) {
        Class<?> component = componentObject.getClass();

        for (Annotation annotation : component.getDeclaredAnnotations()) {
            AnnotationEvents events = registry.get(annotation.annotationType());
            if (events == null || events.onComponent == null) continue;
            events.onComponent.onComponent(annotation, componentObject);
        }

        for (Field field : component.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                AnnotationEvents events = registry.get(annotation.annotationType());
                if (events == null || events.onField == null) continue;
                events.onField.onField(annotation, componentObject, field);
            }
        }

        for (Method method : component.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                AnnotationEvents events = registry.get(annotation.annotationType());
                if (events == null || events.onMethod == null) continue;
                events.onMethod.onMethod(annotation, componentObject, method);
            }
        }
    }

    @Override
    public void registerAnnotation(Class<? extends Annotation> annotation, @Nullable OnMethod onMethod, @Nullable OnField onField, @Nullable OnComponent onComponent) {
        registry.put(annotation, new AnnotationEvents(onMethod, onField, onComponent));
    }

    @Override
    public void unregisterAnnotation(Class<? extends Annotation> annotation) {
        registry.remove(annotation);
    }

    @Override
    public List<Class<? extends Annotation>> getAnnotations() {
        return new ArrayList<>(registry.keySet());
    }

    @Override
    public OnMethod getOnMethod(Class<? extends Annotation> annotation) {
        AnnotationEvents events = registry.get(annotation);
        if (events == null) return null;
        return events.onMethod;
    }

    @Override
    public OnField getOnField(Class<? extends Annotation> annotation) {
        AnnotationEvents events = registry.get(annotation);
        if (events == null) return null;
        return events.onField;
    }

    @Override
    public OnComponent getOnComponent(Class<? extends Annotation> annotation) {
        AnnotationEvents events = registry.get(annotation);
        if (events == null) return null;
        return events.onComponent;
    }

    @NotNull
    public LiberiumPlugin getPlugin() {
        return plugin;
    }

    private static class AnnotationEvents {
        public final OnMethod onMethod;
        public final OnField onField;
        public final OnComponent onComponent;

        public AnnotationEvents(@Nullable OnMethod onMethod, @Nullable OnField onField, @Nullable OnComponent onComponent) {
            this.onMethod = onMethod;
            this.onField = onField;
            this.onComponent = onComponent;
        }
    }
}
