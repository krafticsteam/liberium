package com.kraftics.liberium;

import com.kraftics.liberium.annotation.AnnotationHandler;
import com.kraftics.liberium.annotation.PluginAnnotationHandler;
import com.kraftics.liberium.module.Module;
import com.kraftics.liberium.module.ModuleRegistry;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class LiberiumPlugin extends JavaPlugin {
    protected final AnnotationHandler annotationHandler = new PluginAnnotationHandler(this);

    private final List<Module> modules = new ArrayList<>();
    private final List<Object> components = new ArrayList<>();

    public LiberiumPlugin() {
        for (Class<? extends Module> module : ModuleRegistry.getClasses()) {
            if (module == Module.class) continue;
            enableModule(module);
        }
    }

    @Contract("null -> fail")
    public final void initModule(Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");
        Validate.isTrue(getModule(moduleClass) == null, "Module");

        Module module = ModuleRegistry.createInstance(moduleClass);
        if (module == null) {
            try {
                module = ModuleRegistry.fromDefault(moduleClass);
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException("Invalid module " + moduleClass + ", maybe not registered?", e);
            }
        }

        modules.add(module);
        module.init(this);
    }

    @Contract("null -> fail")
    public final void enableModule(Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");

        Module module = getModule(moduleClass);

        if (module == null)
            initModule(moduleClass);

        module = getModule(moduleClass);
        module.enable();
    }

    @Contract("null -> fail")
    public final void disableModule(Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");

        Module module = getModule(moduleClass);
        if (module == null)
            throw new IllegalStateException("Module " + moduleClass + " is not initialized in " + getClass());

        module.disable();
    }

    @SuppressWarnings("unchecked")
    @Contract("null -> null; !null -> _")
    public final <T extends Module> T getModule(Class<T> moduleClass) {
        for (Module module : modules) {
            if (module.getClass() == moduleClass) {
                return (T) module;
            }
        }
        return null;
    }

    @Contract("null -> fail")
    public final void registerComponent(Object component) {
        Validate.notNull(component, "Component cannot be null");

        components.add(component);
        modules.forEach(module -> module.onComponentRegistry(component));
        annotationHandler.handleComponent(component);
    }

    @NotNull
    public final List<Module> getModules() {
        return new ArrayList<>(modules);
    }

    @NotNull
    public final List<Object> getComponents() {
        return new ArrayList<>(components);
    }

    @NotNull
    public AnnotationHandler getAnnotationHandler() {
        return annotationHandler;
    }
}
