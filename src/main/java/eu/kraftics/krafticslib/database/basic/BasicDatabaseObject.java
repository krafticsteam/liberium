package eu.kraftics.krafticslib.database.basic;

import eu.kraftics.krafticslib.database.DatabaseObject;
import eu.kraftics.krafticslib.database.DatabaseSerializable;

import java.util.HashMap;
import java.util.Map;

public class BasicDatabaseObject implements DatabaseObject {
    private Map<String, Object> map;

    public BasicDatabaseObject(Map<String, Object> map) {
        this.map = map;
    }

    public BasicDatabaseObject() {
        this(new HashMap<>());
    }

    @Override
    public void put(String name, String s) {
        map.put(name, s);
    }

    @Override
    public void put(String name, int i) {
        map.put(name, i);
    }

    @Override
    public void put(String name, long l) {
        map.put(name, l);
    }

    @Override
    public void put(String name, short s) {
        map.put(name, s);
    }

    @Override
    public void put(String name, double d) {
        map.put(name, d);
    }

    @Override
    public void put(String name, float f) {
        map.put(name, f);
    }

    @Override
    public void put(String name, byte b) {
        map.put(name, b);
    }

    @Override
    public void put(String name, DatabaseSerializable o) {
        map.put(name, o.serialize());
    }

    @Override
    public Object get(String name) {
        return map.get(name);
    }

    @Override
    public DatabaseObject getObject(String name) {
        try {
            return new BasicDatabaseObject((Map<String, Object>) get(name));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getString(String name) {
        Object o = get(name);
        return o != null ? o.toString() : null;
    }

    @Override
    public Integer getInteger(String name) {
        Object o = get(name);
        return o instanceof Integer ? (int) o : null;
    }

    @Override
    public Long getLong(String name) {
        Object o = get(name);
        return o instanceof Long ? (long) o : null;
    }

    @Override
    public Short getShort(String name) {
        Object o = get(name);
        return o instanceof Short ? (short) o : null;
    }

    @Override
    public Double getDouble(String name) {
        Object o = get(name);
        return o instanceof Double ? (double) o : null;
    }

    @Override
    public Float getFloat(String name) {
        Object o = get(name);
        return o instanceof Float ? (float) o : null;
    }

    @Override
    public Byte getByte(String name) {
        Object o = get(name);
        return o instanceof Byte ? (byte) o : null;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>(map);
    }
}
