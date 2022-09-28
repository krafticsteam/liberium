package com.kraftics.liberium.module;

import com.kraftics.liberium.LiberiumPlugin;
import com.kraftics.liberium.annotation.AnnotationListenerBuilder;
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
//        this.plugin.getAnnotationProcessor().enableModule(this);
        this.enabled = true;
    }

    public void disable() {
        onDisable();
//        this.plugin.getAnnotationProcessor().disableModule(this);
        this.enabled = false;
    }

    public abstract void onInit();
    public abstract void onEnable();
    public abstract void onDisable();

    public void onComponentRegistry(Object component) {

    }

    public <A extends Annotation> AnnotationListenerBuilder<A> registerAnnotation(Class<A> annotation) {
        return new AnnotationListenerBuilder<>(this, annotation, null);
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
