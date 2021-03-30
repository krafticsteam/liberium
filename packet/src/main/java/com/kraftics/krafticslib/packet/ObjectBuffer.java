package com.kraftics.krafticslib.packet;

import com.kraftics.krafticslib.packet.convert.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectBuffer {
    private Object object;
    private Map<Class<?>, List<Field>> data;

    private ObjectBuffer() {
    }

    public static ObjectBuffer create(Object object) {
        ObjectBuffer buffer = new ObjectBuffer();
        buffer.object = object;

        Map<Class<?>, List<Field>> data = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            List<Field> fields = data.get(field.getType());
            if (fields == null) fields = new ArrayList<>();
            fields.add(field);
            data.put(field.getType(), fields);
        }

        buffer.data = data;
        return buffer;
    }

    public <T> T read(Class<T> type, int index) throws IllegalArgumentException {
        try {
            Field field = getField(type, index);
            if (field == null) throw new IllegalArgumentException("Could not access field");
            if (!field.isAccessible()) field.setAccessible(true);
            return type.cast(field.get(object));
        } catch (ClassCastException | IllegalAccessException e) {
            throw new IllegalArgumentException("Could not access field");
        }
    }

    public <T> T read(ObjectConverter<T> converter, int index) throws IllegalArgumentException {
        return converter.getSpecific(read(converter.getGenericType(), index));
    }

    public ItemStack readItemStack(int index) {
        return read(new ItemStackConverter(), index);
    }

    public BlockData readBlockData(int index) {
        return read(new BlockDataConverter(), index);
    }

    public BlockPosition readBlockPosition(int index) {
        return read(new BlockPositionConverter(), index);
    }

    public Material readBlock(int index) {
        return read(new BlockConverter(), index);
    }

    public ChatComponent readChatComponent(int index) {
        return read(new ChatComponentConverter(), index);
    }

    public void write(Class<?> type, int index, Object object) throws IllegalArgumentException {
        try {
            Field field = getField(type, index);
            if (field == null) throw new IllegalArgumentException("Could not access field");
            if (!field.isAccessible()) field.setAccessible(true);
            field.set(this.object, object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Could not access field", e);
        }
    }

    public <T> void write(ObjectConverter<T> converter, int index, T object) throws IllegalArgumentException {
        write(converter.getGenericType(), index, converter.getGeneric(object));
    }

    public void writeItemStack(int index, ItemStack itemStack) {
        write(new ItemStackConverter(), index, itemStack);
    }

    public void writeBlockData(int index, BlockData data) {
        write(new BlockDataConverter(), index, data);
    }

    public void writeBlockPosition(int index, BlockPosition position) {
        write(new BlockPositionConverter(), index, position);
    }

    public void writeBlock(int index, Material material) {
        write(new BlockConverter(), index, material);
    }

    public void writeChatComponent(int index, ChatComponent component) {
        write(new ChatComponentConverter(), index, component);
    }

    public boolean canWrite(Class<?> type, int index) {
        Field field = getField(type, index);
        return field != null && field.isAccessible() && !Modifier.isFinal(field.getModifiers());
    }

    public boolean has(Class<?> type, int index) {
        return getField(type, index) != null;
    }

    @Nullable
    public Field getField(Class<?> type, int index) {
        try {
            List<Field> fields = data.get(type);
            if (fields == null) return null;
            return fields.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Object getObject() {
        return object;
    }
}
