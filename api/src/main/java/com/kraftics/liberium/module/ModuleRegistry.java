package com.kraftics.liberium.module;

import com.kraftics.liberium.main.MainModule;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ModuleRegistry {
    private static final Map<Class<? extends Module>, Supplier<Module>> registry = new HashMap<>();

    static {
        registry.put(Module.class, () -> null);
        registry.put(MainModule.class, MainModule::new);
    }

    @Nullable
    public static Module createInstance(@NotNull Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        Supplier<Module> supplier = registry.get(module);
        if (supplier == null) return null;
        return supplier.get();
    }

    public static Module fromDefault(@NotNull Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        try {
            Constructor<? extends Module> constructor = module.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Could not create new module from default constructor", e);
        }
    }

    public static boolean isRegistered(@NotNull Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        return registry.containsKey(module);
    }

    @Nullable
    public static Supplier<Module> get(@NotNull Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        return registry.get(module);
    }

    public static void register(@NotNull Class<? extends Module> module, @NotNull Supplier<Module> factory) {
        Validate.notNull(module, "Module cannot be null");
        Validate.notNull(factory, "Factory cannot be null");
        Validate.isTrue(!isRegistered(module), "This module is already registered");

        registry.put(module, factory);
    }

    public static Set<Class<? extends Module>> getClasses() {
        return registry.keySet();
    }
}
