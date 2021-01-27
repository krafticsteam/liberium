package com.kraftics.krafticslib.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Object containing a map of items
 *
 * @see Database
 * @see Collection
 * @author Panda885
 */
public class DatabaseObject implements DatabaseSerializable {
    private final Map<String, Object> map = new HashMap<>();

    public DatabaseObject put(String name, Object o) {
        map.put(name, o);
        return this;
    }

    public DatabaseObject clear() {
        map.clear();
        return this;
    }

    public int size() {
        return map.size();
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }

    public Object get(String name) {
        return map.get(name);
    }

    public DatabaseObject getObject(String name) {
        Object o = get(name);
        return o instanceof DatabaseObject ? (DatabaseObject) o : null;
    }

    public String getString(String name) {
        Object o = get(name);
        return o != null ? o.toString() : null;
    }

    public Integer getInteger(String name) {
        Object o = get(name);
        return o instanceof Integer ? (int) o : null;
    }

    public Long getLong(String name) {
        Object o = get(name);
        return o instanceof Long ? (long) o : null;
    }

    public Short getShort(String name) {
        Object o = get(name);
        return o instanceof Short ? (short) o : null;
    }

    public Double getDouble(String name) {
        Object o = get(name);
        return o instanceof Double ? (double) o : null;
    }

    public Float getFloat(String name) {
        Object o = get(name);
        return o instanceof Float ? (float) o : null;
    }

    public Byte getByte(String name) {
        Object o = get(name);
        return o instanceof Byte ? (byte) o : null;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseObject)) return false;
        DatabaseObject object = (DatabaseObject) o;
        return map.equals(object.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
