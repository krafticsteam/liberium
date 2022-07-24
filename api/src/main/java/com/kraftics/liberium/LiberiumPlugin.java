package com.kraftics.liberium;

import com.kraftics.liberium.annotation.AnnotationProcessor;
import com.kraftics.liberium.annotation.WrapperAnnotationProcessor;
import com.kraftics.liberium.main.MainModule;
import com.kraftics.liberium.module.Module;
import com.kraftics.liberium.module.ModuleRegistry;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class LiberiumPlugin extends JavaPlugin {
    protected final AnnotationProcessor annotationProcessor = new WrapperAnnotationProcessor();

    private final List<Module> modules = new ArrayList<>();

    public LiberiumPlugin() {
        super();

        useModule(MainModule.class);

        for (Class<? extends Module> module : ModuleRegistry.getClasses()) {
            if (module == Module.class) continue;
            enableModule(module);
        }

        onInit();
    }

    public void onInit() {

    }

    @Override
    public void onEnable() {
        super.onEnable();

        for (Module module : modules) {
            if (!module.isInitialized() || module.isEnabled()) continue;
            module.enable();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        for (Module module : modules) {
            // TODO: Add logging?
            if (!module.isInitialized() || !module.isEnabled()) continue;
            module.disable();
        }
    }

    private void initModule(@NotNull Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");
        Validate.isTrue(getModule(moduleClass) == null, "Module is already initialized");

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

    public final void useModule(@NotNull Class<? extends Module> moduleClass) {
        try {
            Validate.notNull(moduleClass, "Module class cannot be null");

            Module module = ModuleRegistry.createInstance(moduleClass);
            if (module == null) {
                module = ModuleRegistry.fromDefault(moduleClass);
            }

            modules.add(module);
        } catch (Exception e) {
            // We don't want to crash the plugin
            e.printStackTrace();
        }
    }

    public final void enableModule(@NotNull Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");

        Module module = getModule(moduleClass);

        Validate.notNull(module, "Module " + moduleClass + " is not used");
        Validate.isTrue(module.isInitialized(), "Module " + moduleClass + " is not initialized yet");

        module.enable();
    }

    public final void disableModule(@NotNull Class<? extends Module> moduleClass) {
        Validate.notNull(moduleClass, "Module class cannot be null");

        Module module = getModule(moduleClass);

        Validate.notNull(module, "Module " + moduleClass + " is not used");
        Validate.isTrue(module.isInitialized(), "Module " + moduleClass + " is not initialized yet");

        module.disable();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public final <T extends Module> T getModule(Class<T> moduleClass) {
        for (Module module : modules) {
            if (module.getClass() == moduleClass) {
                return (T) module;
            }
        }
        return null;
    }

    public AnnotationProcessor getAnnotationProcessor() {
        return annotationProcessor;
    }

    @NotNull
    public final List<Module> getModules() {
        return new ArrayList<>(modules);
    }
}
