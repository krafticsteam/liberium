package com.kraftics.krafticslib.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.function.Function;

public interface Document {
    @NotNull
    Document add(String name, Object o);

    @NotNull
    Document remove(String name);

    boolean has(String name);

    boolean has(String name, Class<?> clazz);

    @Nullable
    Object get(String name);

    @Nullable
    default <T> T get(String name, Function<Object, T> cast) {
        Object o = get(name);
        return cast.apply(o);
    }

    @Nullable
    default <T> T get(String name, Class<T> clazz) {
        return get(name, o -> o == null || !clazz.isAssignableFrom(o.getClass()) ? null : clazz.cast(o));
    }

    @Nullable
    default String getString(String name) {
        return get(name, o -> o == null ? null : o.toString());
    }

    @Nullable
    default Short getShort(String name) {
        return get(name, Short.class);
    }

    @Nullable
    default Integer getInteger(String name) {
        return get(name, Integer.class);
    }

    @Nullable
    default Long getLong(String name) {
        return get(name, Long.class);
    }

    @Nullable
    default Float getFloat(String name) {
        return get(name, Float.class);
    }

    @Nullable
    default Double getDouble(String name) {
        return get(name, Double.class);
    }

    @Nullable
    default BigDecimal getBigDecimal(String name) {
        return get(name, BigDecimal.class);
    }

    @Nullable
    default Boolean getBoolean(String name) {
        return get(name, Boolean.class);
    }

    @Nullable
    default Byte getByte(String name) {
        return get(name, Byte.class);
    }

    @Nullable
    default Byte[] getBytes(String name) {
        return get(name, Byte[].class);
    }

    @Nullable
    default Date getDate(String name) {
        return get(name, Date.class);
    }

    @Nullable
    default Time getTime(String name) {
        return get(name, Time.class);
    }

    @Nullable
    default Timestamp getTimestamp(String name) {
        return get(name, Timestamp.class);
    }

}
