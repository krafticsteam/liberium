package com.kraftics.liberium.reflection;

import com.kraftics.liberium.reflection.exception.ReflectionNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static java.util.Objects.requireNonNull;

public class ClassWrapper extends ObjectWrapper {
    private final Class<?> wrappedClass;

    protected ClassWrapper(Class<?> wrappedClass) {
        super(null);
        this.wrappedClass = wrappedClass;
    }

    @NotNull
    public static ClassWrapper wrap(@NotNull Class<?> clazz) {
        return new ClassWrapper(requireNonNull(clazz, "Class cannot be null"));
    }

    @Nullable
    public static ClassWrapper wrapNullable(@Nullable Class<?> clazz) {
        return clazz == null ? null : wrap(clazz);
    }

    @NotNull
    @Override
    public Method getMethod(String method, Object... args) throws ReflectionNotFoundException {
        Method result = super.getMethod(method, args);
        if (!Modifier.isStatic(result.getModifiers())) throw new ReflectionNotFoundException("The found method \"" + result.getName() + "\" is not static");
        return result;
    }

    @NotNull
    public Class<?> getWrappedClass() {
        return wrappedClass;
    }

    @NotNull
    @Override
    public Object getWrappedObject() {
        return wrappedClass;
    }
}
