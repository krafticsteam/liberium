package com.kraftics.krafticslib.database;

/**
 * Object containing a map of items
 *
 * @see Database
 * @see Collection
 * @author Panda885
 */
public interface DatabaseObject extends DatabaseSerializable {

    default DatabaseObject put(String name, String s) {
        put(name, (Object) s);
        return this;
    }

    default DatabaseObject put(String name, int i) {
        put(name, (Object) i);
        return this;
    }

    default DatabaseObject put(String name, long l) {
        put(name, (Object) l);
        return this;
    }

    default DatabaseObject put(String name, short s) {
        put(name, (Object) s);
        return this;
    }

    default DatabaseObject put(String name, double d) {
        put(name, (Object) d);
        return this;
    }

    default DatabaseObject put(String name, float f) {
        put(name, (Object) f);
        return this;
    }

    default DatabaseObject put(String name, byte b) {
        put(name, (Object) b);
        return this;
    }

    DatabaseObject put(String name, Object o);

    Object get(String name);

    default DatabaseObject getObject(String name) {
        Object o = get(name);
        return o instanceof DatabaseObject ? (DatabaseObject) o : null;
    }

    default String getString(String name) {
        Object o = get(name);
        return o != null ? o.toString() : null;
    }

    default Integer getInteger(String name) {
        Object o = get(name);
        return o instanceof Integer ? (int) o : null;
    }

    default Long getLong(String name) {
        Object o = get(name);
        return o instanceof Long ? (long) o : null;
    }

    default Short getShort(String name) {
        Object o = get(name);
        return o instanceof Short ? (short) o : null;
    }

    default Double getDouble(String name) {
        Object o = get(name);
        return o instanceof Double ? (double) o : null;
    }

    default Float getFloat(String name) {
        Object o = get(name);
        return o instanceof Float ? (float) o : null;
    }

    default Byte getByte(String name) {
        Object o = get(name);
        return o instanceof Byte ? (byte) o : null;
    }
}
