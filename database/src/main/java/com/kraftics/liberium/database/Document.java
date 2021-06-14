package com.kraftics.liberium.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Document {
    private final Map<String, Object> map;

    public Document(Map<String, Object> map) {
        this.map = map;
    }

    public Document() {
        this(new HashMap<>());
    }

    @NotNull
    public Map<String, Object> serialize() {
        return map;
    }

    @NotNull
    public Document add(String name, Object o) {
        map.put(name, o);
        return this;
    }

    @NotNull
    public Document remove(String name) {
        map.remove(name);
        return this;
    }

    public boolean has(String name) {
        return map.containsKey(name);
    }

    public boolean has(String name, Class<?> clazz) {
        Object o = get(name);
        return o != null && clazz.isAssignableFrom(o.getClass());
    }

    @Nullable
    public Object get(String name) {
        return map.get(name);
    }

    @Nullable
    public <T> T get(String name, Function<Object, T> cast) {
        Object o = get(name);
        return cast.apply(o);
    }

    @Nullable
    public <T> T get(String name, Class<T> clazz) {
        return get(name, o -> o == null || !clazz.isAssignableFrom(o.getClass()) ? null : clazz.cast(o));
    }

    @Nullable
    public String getString(String name) {
        return get(name, o -> o == null ? null : o.toString());
    }

    @Nullable
    public Short getShort(String name) {
        return get(name, Short.class);
    }

    @Nullable
    public Integer getInteger(String name) {
        return get(name, Integer.class);
    }

    @Nullable
    public Long getLong(String name) {
        return get(name, Long.class);
    }

    @Nullable
    public Float getFloat(String name) {
        return get(name, Float.class);
    }

    @Nullable
    public Double getDouble(String name) {
        return get(name, Double.class);
    }

    @Nullable
    public BigDecimal getBigDecimal(String name) {
        return get(name, BigDecimal.class);
    }

    @Nullable
    public Boolean getBoolean(String name) {
        return get(name, Boolean.class);
    }

    @Nullable
    public Byte getByte(String name) {
        return get(name, Byte.class);
    }

    @Nullable
    public Byte[] getBytes(String name) {
        return get(name, Byte[].class);
    }

    @Nullable
    public Date getDate(String name) {
        return get(name, Date.class);
    }

    @Nullable
    public Time getTime(String name) {
        return get(name, Time.class);
    }

    @Nullable
    public Timestamp getTimestamp(String name) {
        return get(name, Timestamp.class);
    }

}
