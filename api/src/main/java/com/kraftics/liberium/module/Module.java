package com.kraftics.liberium.module;

import com.kraftics.liberium.LiberiumPlugin;
import com.kraftics.liberium.annotation.OnComponent;
import com.kraftics.liberium.annotation.OnField;
import com.kraftics.liberium.annotation.OnMethod;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.function.Function;

public abstract class Module {
    protected LiberiumPlugin plugin;

    private boolean initialized = false;
    private boolean enabled = false;

    public final void init(LiberiumPlugin plugin) {
        this.plugin = plugin;
        onInit();
        initialized = true;
    }

    public void enable() {
        onEnable();
        this.enabled = true;
    }

    public void disable() {
        onDisable();
        this.enabled = false;
    }

    public abstract void onInit();
    public abstract void onEnable();
    public abstract void onDisable();

    public void onComponentRegistry(Object component) {

    }

    public void registerAnnotation(Class<? extends Annotation> annotation, @Nullable OnMethod onMethod, @Nullable OnField onField, @Nullable OnComponent onComponent) {
        plugin.getAnnotationHandler().registerAnnotation(annotation, onMethod, onField, onComponent);
    }

    public void registerFieldAnnotation(Class<? extends Annotation> annotation, Function<Object, Object> function) {
        registerAnnotation(annotation, null, (annot, component, field) -> {
            if (!field.isAccessible())
                field.setAccessible(true);

            Object originalValue;
            try {
                originalValue = field.get(component);
            } catch (IllegalAccessException|ExceptionInInitializerError|IllegalArgumentException e) {
                e.printStackTrace();
                originalValue = null;
            }

            Object changedValue = function.apply(originalValue);

            try {
                field.set(component, changedValue);
            } catch (IllegalAccessException|ExceptionInInitializerError|IllegalArgumentException e) {
                e.printStackTrace();
            }
        }, null);
    }

    public void unregisterAnnotation(Class<? extends Annotation> annotation) {
        plugin.getAnnotationHandler().unregisterAnnotation(annotation);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LiberiumPlugin getPlugin() {
        return plugin;
    }
}
