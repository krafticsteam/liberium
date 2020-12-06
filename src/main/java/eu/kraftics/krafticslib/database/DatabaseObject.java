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
    String getString(String name);
    Integer getInteger(String name);
    Long getLong(String name);
    Short getShort(String name);
    Double getDouble(String name);
    Float getFloat(String name);
    Byte getByte(String name);
}
