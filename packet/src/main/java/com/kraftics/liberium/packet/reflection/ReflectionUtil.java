package com.kraftics.liberium.packet.reflection;

import com.kraftics.liberium.packet.convert.ItemStackConverter;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionUtil {
    private static final Class<?> bossBattleClass = Reflection.getNMSClass("BossBattle");
    private static final Class<?> craftPlayerClass = Reflection.getCraftClass("entity.CraftPlayer");
    private static final Class<?> nmsPlayerClass = Reflection.getNMSClass("EntityPlayer");
    private static final Class<?> playerConnectionClass = Reflection.getNMSClass("PlayerConnection");
    private static final Class<?> packetClass = Reflection.getNMSClass("Packet");
    private static final Class<?> craftMagicNumberClass = Reflection.getCraftClass("util.CraftMagicNumbers");
    private static final Class<?> minecraftKeyClass = Reflection.getNMSClass("MinecraftKey");

    private static final MethodInvoker<Object> getPlayerHandleMethod = Reflection.getMethod(craftPlayerClass, "getHandle", Object.class);

    private static final FieldAccessor<Object> playerConnectionField = Reflection.getField(nmsPlayerClass, "playerConnection", Object.class);

    private ReflectionUtil() {

    }

    public static Entity getEntity(World world, int id) {
        for (Entity entity : world.getEntities()) {
            if (entity.getEntityId() == id) {
                return entity;
            }
        }
        return null;
    }

    public static List<Field> getFields(Class<?> clazz, Class<?> type) {
        List<Field> fields = new ArrayList<>();

        for (Field field : clazz.getFields()) {
            if (type.isAssignableFrom(field.getType())) {
                fields.add(field);
            }
        }

        return fields;
    }

    public static Class<?> getBossBattleClass() {
        return bossBattleClass;
    }

    public static Class<?> getMinecraftKeyClass() {
        return minecraftKeyClass;
    }

    public static Class<?> getCraftItemStackClass() {
        return ItemStackConverter.CRAFT;
    }

    public static Class<?> getCraftPlayerClass() {
        return craftPlayerClass;
    }

    public static Class<?> getNmsPlayerClass() {
        return nmsPlayerClass;
    }

    public static Class<?> getPlayerConnectionClass() {
        return playerConnectionClass;
    }

    public static Class<?> getPacketClass() {
        return packetClass;
    }

    public static Class<?> getCraftMagicNumberClass() {
        return craftMagicNumberClass;
    }

    public static MethodInvoker<Object> getPlayerHandleMethod() {
        return getPlayerHandleMethod;
    }

    public static MethodInvoker<Object> getHandleMethod(Class<?> clazz) {
        return Reflection.getMethod(clazz, "getHandle", Object.class);
    }

    public static FieldAccessor<Object> getHandleField(Class<?> clazz) {
        return Reflection.getField(clazz, "handle", Object.class);
    }

    public static FieldAccessor<Object> getPlayerConnectionField() {
        return playerConnectionField;
    }

    public static boolean is(Class<?> clazz, Class<?> test) {
        return clazz != null && test != null && clazz.isAssignableFrom(test);
    }

    public static boolean is(Class<?> clazz, Object o) {
        return o != null && is(clazz, o.getClass());
    }

    public static Object unwrap(Object bukkitWrapper) {
        try {
            return getHandleMethod(bukkitWrapper.getClass()).invoke(bukkitWrapper);
        } catch (ReflectionException e) {
            return getHandleField(bukkitWrapper.getClass()).get(bukkitWrapper);
        }
    }
}
