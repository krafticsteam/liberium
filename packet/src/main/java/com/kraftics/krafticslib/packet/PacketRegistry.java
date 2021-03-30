package com.kraftics.krafticslib.packet;

import com.kraftics.krafticslib.packet.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    public static final Map<Class<?>, PacketType> DEFAULT_REGISTRY = new HashMap<>();

    static {
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutAbilities"), PacketType.Play.Out.ABILITIES);
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutAnimation"), PacketType.Play.Out.ANIMATION);
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutBlockChange"), PacketType.Play.Out.BLOCK_CHANGE);
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutBoss"), PacketType.Play.Out.BOSS);
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutSpawnEntityLiving"), PacketType.Play.Out.SPAWN_LIVING);
        DEFAULT_REGISTRY.put(Reflection.getNMSClass("PacketPlayOutSpawnEntityExperienceOrb"), PacketType.Play.Out.SPAWN_EXPERIENCE);
    }

    private final Map<Class<?>, PacketType> registry;

    public PacketRegistry(boolean defaults) {
        this.registry = defaults ? DEFAULT_REGISTRY : new HashMap<>();
    }

    public void register(Class<?> clazz, PacketType packet) {
        registry.put(clazz, packet);
    }

    public PacketType get(Object o) {
        return get(o.getClass());
    }

    public PacketType get(Class<?> o) {
        return registry.get(o);
    }
}
