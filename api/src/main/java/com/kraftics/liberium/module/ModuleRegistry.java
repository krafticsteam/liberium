package com.kraftics.liberium.module;

import com.kraftics.liberium.module.main.MainModule;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

// TODO: Add documentation
public class ModuleRegistry {
    private static final Map<Class<? extends Module>, Supplier<Module>> registry = new HashMap<>();

    static {
        registry.put(Module.class, () -> null);
        registry.put(MainModule.class, MainModule::new);
    }

    @Nullable
    @Contract("null -> fail; !null -> _")
    public static Module createInstance(Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        Supplier<Module> supplier = registry.get(module);
        if (supplier == null) return null;
        return supplier.get();
    }

    @Contract("null -> fail; !null -> !null")
    public static Module fromDefault(Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        try {
            Constructor<? extends Module> constructor = module.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Could not create new module from default constructor", e);
        }
    }

    @Contract("null -> fail; !null -> _")
    public static boolean isRegistered(Class<? extends Module> module) {
        Validate.notNull(module, "Module cannot be null");

        return registry.containsKey(module);
    }

    @Contract("null, !null -> fail; !null, null -> fail")
    public static void register(Class<? extends Module> module, Supplier<Module> supplier) {
        Validate.notNull(module, "Module cannot be null");
        Validate.notNull(supplier, "Supplier cannot be null");
        Validate.isTrue(!isRegistered(module), "This module is already registered");

        registry.put(module, supplier);
    }

    public static Set<Class<? extends Module>> getClasses() {
        return registry.keySet();
    }
}
