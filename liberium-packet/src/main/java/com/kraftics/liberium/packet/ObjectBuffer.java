package com.kraftics.liberium.packet;

import com.kraftics.liberium.packet.convert.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to modify fields of a object
 */
public class ObjectBuffer {
    private final Object object;
    private final Map<Class<?>, List<Field>> data;

    public ObjectBuffer(PacketType type) {
        this(PacketRegistry.getNew(type));
    }

    public ObjectBuffer(Object object) {
        this.object = object;

        Map<Class<?>, List<Field>> data = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            List<Field> fields = data.get(field.getType());
            if (fields == null) fields = new ArrayList<>();
            fields.add(field);
            data.put(field.getType(), fields);
        }

        this.data = data;
    }

    /**
     * Reads data from a field.
     *
     * @param type The type of the field
     * @param index The index of the field starting from first field of this type (same as ProtocolLib)
     * @param <T> The type of this field
     * @return The data
     * @throws IllegalArgumentException If the field could not be accessed
     */
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

    /**
     * Reads data from a field and converts it.
     *
     * @param converter The converter to convert this field
     * @param index The index of the field starting from first field of this type
     * @param <T> The type of this field
     * @return The data
     * @throws IllegalArgumentException If the field could not be accessed
     */
    public <T> T read(ObjectConverter<T> converter, int index) throws IllegalArgumentException {
        return converter.getSpecific(read(converter.getGenericType(), index));
    }

    /**
     * Reads an item stack
     *
     * @param index The index
     * @return The ItemStack
     */
    public ItemStack readItemStack(int index) {
        return read(new ItemStackConverter(), index);
    }

    /**
     * Reads a block data
     *
     * @param index The index
     * @return The BlockData
     */
    public BlockData readBlockData(int index) {
        return read(new BlockDataConverter(), index);
    }

    /**
     * Reads a block position
     *
     * @param index The index
     * @return The BlockPosition
     */
    public BlockPosition readBlockPosition(int index) {
        return read(new BlockPositionConverter(), index);
    }

    /**
     * Reads a block and converts it to Material
     *
     * @param index The index
     * @return The Material
     */
    public Material readBlock(int index) {
        return read(new BlockConverter(), index);
    }

    /**
     * Reads a chat component
     *
     * @param index The index
     * @return The ChatComponent
     */
    public ChatComponent readChatComponent(int index) {
        return read(new ChatComponentConverter(), index);
    }

    /**
     * Reads a minecraft key
     *
     * @param index The index
     * @return The MinecraftKey
     */
    public MinecraftKey readMinecraftKey(int index) {
        return read(new MinecraftKeyConverter(), index);
    }

    /**
     * Writes data to a field
     *
     * @param type The type of the field
     * @param index The index of the field starting from first field of this type (same as ProtocolLib)
     * @param object The object to write
     * @throws IllegalArgumentException If the field could not be accessed
     */
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

    /**
     * Writes data to a field
     *
     * @param converter The converter to convert this field
     * @param index The index of the field starting from first field of this type (same as ProtocolLib)
     * @param object The object to write
     * @param <T> The type of the field
     * @throws IllegalArgumentException If the field could not be accessed
     */
    public <T> void write(ObjectConverter<T> converter, int index, T object) throws IllegalArgumentException {
        write(converter.getGenericType(), index, converter.getGeneric(object));
    }

    /**
     * Writes an item stack
     *
     * @param index The index
     * @param itemStack The ItemStack
     */
    public void writeItemStack(int index, ItemStack itemStack) {
        write(new ItemStackConverter(), index, itemStack);
    }

    /**
     * Writes a block data
     *
     * @param index The index
     * @param data The BlockData
     */
    public void writeBlockData(int index, BlockData data) {
        write(new BlockDataConverter(), index, data);
    }

    /**
     * Writes a block position
     *
     * @param index The index
     * @param position The BlockPosition
     */
    public void writeBlockPosition(int index, BlockPosition position) {
        write(new BlockPositionConverter(), index, position);
    }

    /**
     * Writes a block
     *
     * @param index The index
     * @param material The block (Material)
     */
    public void writeBlock(int index, Material material) {
        write(new BlockConverter(), index, material);
    }

    /**
     * Writes a chat component
     *
     * @param index The index
     * @param component The ChatComponent
     */
    public void writeChatComponent(int index, ChatComponent component) {
        write(new ChatComponentConverter(), index, component);
    }

    /**
     * Writes a minecraft key
     *
     * @param index The index
     * @param component The MinecraftKey
     */
    public void writeMinecraftKey(int index, MinecraftKey component) {
        write(new MinecraftKeyConverter(), index, component);
    }

    /**
     * If you can write to a field
     *
     * @param type The type
     * @param index The index
     * @return If you can write to a field
     */
    public boolean canWrite(Class<?> type, int index) {
        Field field = getField(type, index);
        return field != null && field.isAccessible() && !Modifier.isFinal(field.getModifiers());
    }

    /**
     * If the object has a field
     *
     * @param type The type
     * @param index The index
     * @return If the object has a field
     */
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
