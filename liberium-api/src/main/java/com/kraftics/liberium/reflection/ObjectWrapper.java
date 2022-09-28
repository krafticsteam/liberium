package com.kraftics.liberium.reflection;

import com.kraftics.liberium.reflection.exception.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class ObjectWrapper {
    private final Object object;

    protected ObjectWrapper(Object object) {
        this.object = object;
    }

    @NotNull
    public static ObjectWrapper wrap(@NotNull Object object) {
        requireNonNull(object, "Object cannot be null");
        // TODO: Handle primitive objects
        if (object instanceof Class) return ClassWrapper.wrap((Class<?>) object);
        return new ObjectWrapper(object);
    }

    @Nullable
    public static ObjectWrapper wrapNullable(@Nullable Object object) {
        return object == null ? null : wrap(object);
    }

    @NotNull
    public ObjectWrapper read(@NotNull String name) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public <T> T read(@NotNull Class<T> clazz, @NotNull String name) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public ObjectWrapper read(@NotNull Class<?> type, int index) {
        throw new UnsupportedOperationException();
    }

    public void write(@NotNull String name, @NotNull Object value) {
        throw new UnsupportedOperationException();
    }

    public <T> void write(@NotNull Class<T> type, int index, T object) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ObjectWrapper invoke(@NotNull String method, @Nullable Object... args) throws ReflectionInaccessibleException, ReflectionInvocationException, ReflectionNotFoundException {
        requireNonNull(method, "Method cannot be null");
        try {
            // TODO: Unwrap all ObjectWrappers in args
            return ObjectWrapper.wrapNullable(getMethod(method, args).invoke(object, args));
        } catch (IllegalAccessException e) {
            throw new ReflectionInaccessibleException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ReflectionInvocationException(e.getMessage());
        }
    }

    @Nullable
    public ObjectWrapper invoke(@NotNull String method, @Nullable ObjectWrapper... args) throws ReflectionInaccessibleException, ReflectionInvocationException, ReflectionNotFoundException {
        return this.invoke(method, Arrays.stream(args).map(wrapper -> wrapper != null ? wrapper.getWrappedObject() : null).toArray(Object[]::new));
    }

    @NotNull
    public Method getMethod(String method, Object... args) throws ReflectionNotFoundException {
        try {
            return object.getClass().getMethod(method, Arrays.stream(args).map(o -> o != null ? o.getClass() : null).toArray(Class[]::new));
        } catch (NoSuchMethodException e) {
            throw new ReflectionNotFoundException(e.getMessage());
        }
    }

    @NotNull
    public <T> T cast(Class<T> type) throws ReflectionCastException {
        try {
            return type.cast(getWrappedObject());
        } catch (ClassCastException e) {
            throw new ReflectionCastException(e.getMessage());
        }
    }

    @NotNull
    public Object getWrappedObject() {
        return object;
    }
}
