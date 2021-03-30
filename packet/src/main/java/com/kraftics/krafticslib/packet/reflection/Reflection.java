package com.kraftics.krafticslib.packet.reflection;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Reflection handler for NMS and CraftBukkit.
 * <p>
 * This class is used to get classes that contains version in their package names.
 * It's easy to use and straightforward.
 * </p>
 *
 * @author Kraftics
 * @since 1.0.0
 */
public class Reflection {
    public static final String VERSION = detectVersion();

    private Reflection() {
    }

    /**
     * Detects the version of this server for NMS or CraftBukkit classes
     *
     * @throws ReflectionException if the version could not be detected
     * @return the detected version
     */
    @NotNull
    public static String detectVersion() throws ReflectionException {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (IndexOutOfBoundsException e) {
            throw new ReflectionException("Could not detect version using this server", e);
        }
    }

    /**
     * Gets a NMS (net.minecraft.server) class
     *
     * @param path path (name) of the class
     * @throws ReflectionException When the nms class was not found
     * @return the NMS class, null if not found
     */
    public static Class<?> getNMSClass(@NotNull String path) throws ReflectionException {
        Validate.notNull(path, "Path cannot be null");

        try {
            return Class.forName("net.minecraft.server." + VERSION + '.' + path);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Could not find nms class: " + path, e);
        }
    }

    /**
     * Gets a CraftBukkit (org.bukkit.craftbukkit) class
     *
     * @param path path (name) of the class
     * @throws ReflectionException When the craft class was not found
     * @return the CraftBukkit class, null if not found
     */
    public static Class<?> getCraftClass(@NotNull String path) throws ReflectionException {
        Validate.notNull(path, "Path cannot be null");

        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + '.' + path);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Could not find craft class: " + path, e);
        }
    }

    /**
     * Gets field from class by name
     *
     * @param target The class to search the field in
     * @param type The class of the field type
     * @param index The index of the field
     * @param <T> The type of the field
     * @throws ReflectionException When the field was not found
     * @return A new {@link FieldAccessor} instance
     * @see #getField(Class, String, Class, int)
     */
    public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> type, int index) throws ReflectionException {
        return getField(target, null, type, index);
    }

    /**
     * Gets field from class by name
     *
     * @param target The class to search the field in
     * @param name The name of the field
     * @param type The class of the field type
     * @param <T> The type of the field
     * @throws ReflectionException When the field was not found
     * @return A new {@link FieldAccessor} instance
     * @see #getField(Class, String, Class, int)
     */
    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> type) throws ReflectionException {
        return getField(target, name, type, 0);
    }

    /**
     * Gets field from class by name
     * <p>
     * This method runs a loop through all fields
     * and checks if the field has valid name, type and index.
     * If it founds a valid field, it makes it accessible
     * and creates new {@link FieldAccessor} class.
     * </p>
     *
     * @param target The class to search the field in
     * @param name The name of the field
     * @param type The class of the field type
     * @param index The index of the field
     * @param <T> The type of the field
     * @throws ReflectionException When the field was not found
     * @return A new {@link FieldAccessor} instance.
     */
    @NotNull
    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> type, int index) throws ReflectionException {
        for (final Field field : target.getDeclaredFields()) {
            if ((name != null && !field.getName().equals(name)) || !type.isAssignableFrom(field.getType()) || index-- > 0) continue;
            if (!field.isAccessible()) field.setAccessible(true);

            return new FieldAccessor<T>() {
                @SuppressWarnings("unchecked")
                @Override
                public T get(Object target) {
                    try {
                        return (T) field.get(target);
                    } catch (IllegalAccessException e) {
                        throw new ReflectionException("Field is not accessible", e);
                    }
                }

                @Override
                public void set(Object target, Object value) {
                    try {
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        throw new ReflectionException("Field is not accessible", e);
                    }
                }

                @Override
                public boolean has(Object target) {
                    return field.getDeclaringClass().isAssignableFrom(target.getClass());
                }
            };
        }

        if (target.getSuperclass() != null) return getField(target.getSuperclass(), name, type, index);
        throw new ReflectionException("Could not find field " + name);
    }

    /**
     * Gets method from class by name and parameters (arguments)
     * <p>
     * This runs a loop through all methods in a class
     * and checks if the method has valid name and type.
     * If it founds a valid method, it makes it accessible
     * and creates new {@link MethodInvoker} class.
     * </p>
     *
     * @param target The class to search the method in
     * @param name The name of the method
     * @param returnType The class of the return type
     * @param params The parameters (arguments) of the method
     * @param <T> The type of the return type
     * @throws ReflectionException When the method was not found
     * @return A new {@link MethodInvoker} instance.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> MethodInvoker<T> getMethod(Class<?> target, String name, Class<T> returnType, Class<?>... params) throws ReflectionException {
        for (final Method method : target.getDeclaredMethods()) {
            if (!method.getName().equals(name) || !returnType.isAssignableFrom(method.getReturnType()) || !Arrays.equals(method.getParameterTypes(), params)) continue;
            if (!method.isAccessible()) method.setAccessible(true);

            return (o, args) -> {
                try {
                    return (T) method.invoke(o, args);
                } catch (IllegalAccessException e) {
                    throw new ReflectionException("Method is not accessible", e);
                } catch (InvocationTargetException e) {
                    throw new ReflectionException("Cannot invoke method " + method.getName(), e);
                }
            };
        }

        if (target.getSuperclass() != null) return getMethod(target.getSuperclass(), name, returnType, params);
        throw new ReflectionException("Could not find method " + name);
    }

    /**
     * Gets constructor from class by parameters (arguments)
     * <p>
     * This runs the {@link Class#getDeclaredConstructor getDeclaredConstructor} method.
     * If it founds a valid constructor, it makes it accessible
     * and creates new {@link ConstructorInvoker} class.
     * </p>
     *
     * @param target The class to search the constructor in
     * @param params The parameters (arguments) of the constructor
     * @param <T> The type of the class
     * @throws ReflectionException When the constructor was not found
     * @return A new {@link ConstructorInvoker} instance.
     */
    @NotNull
    public static <T> ConstructorInvoker<T> getConstructor(Class<T> target, Class<?>... params) throws ReflectionException {
        try {
            final Constructor<T> constructor = target.getDeclaredConstructor(params);
            if (!constructor.isAccessible()) constructor.setAccessible(true);

            return args -> {
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException | InvocationTargetException e) {
                    throw new ReflectionException("Cannot invoke constructor of " + constructor.getName(), e);
                } catch (IllegalAccessException e) {
                    throw new ReflectionException("Constructor is not accessible", e);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new ReflectionException("Could not find constructor", e);
        }
    }
}
