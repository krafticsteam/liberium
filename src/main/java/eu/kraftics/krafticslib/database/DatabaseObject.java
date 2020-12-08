package eu.kraftics.krafticslib.database;

public interface DatabaseObject extends DatabaseSerializable {
    void put(String name, String s);
    void put(String name, int i);
    void put(String name, long l);
    void put(String name, short s);
    void put(String name, double d);
    void put(String name, float f);
    void put(String name, byte b);
    void put(String name, DatabaseSerializable o);

    Object get(String name);
    DatabaseObject getObject(String name);

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
