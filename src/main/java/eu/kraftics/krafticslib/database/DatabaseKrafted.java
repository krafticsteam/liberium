package eu.kraftics.krafticslib.database;

import eu.kraftics.krafticslib.database.basic.BasicCollection;
import eu.kraftics.krafticslib.utils.KraftUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;

public class DatabaseKrafted implements Database {
    private Map<String, Collection> collections;
    private File file;

    public DatabaseKrafted(File file) {
        this.file = file;
        this.collections = new HashMap<>();
    }

    @Override
    public Collection getCollection(String name) {
        return collections.get(name);
    }

    @Override
    public List<Collection> getCollections() {
        return new LinkedList<>(collections.values());
    }

    @Override
    public Collection createCollection(String name) {
        Collection c = new BasicCollection(name);
        collections.put(name, c);
        return c;
    }

    @Override
    public void removeCollection(String name) {
        collections.remove(name);
    }

    public File getFile() {
        return file;
    }

    @Override
    public void push() {

    }

    @Override
    public void pull() {

    }

    private byte[] toBytes(DatabaseObject object) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(5120);
        Map<String, Object> map = object.serialize();

        map.forEach((name, o) -> {

            Class<?> clazz = o.getClass();
            if (clazz == Integer.class) {
                buffer.putInt((int) o);
            } else if (clazz == Short.class) {
                buffer.putShort((short) o);
            } else if (clazz == Long.class) {
                buffer.putLong((long) o);
            } else if (clazz == Double.class) {
                buffer.putDouble((double) o);
            } else if (clazz == Float.class) {
                buffer.putFloat((float) o);
            } else if (clazz == Byte.class) {
                buffer.put((byte) o);
            } else if (o instanceof String) {
                KraftUtils.putString(buffer, (String) o);
            } else if (o instanceof Map) {

            }
        });

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        return bytes;
    }
}
