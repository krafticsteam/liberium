package com.kraftics.liberium.packet.convert;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class MinecraftKey {
    public static final String MINECRAFT = "minecraft";

    private final String key;
    private final String namespace;

    public MinecraftKey(String namespace, String key) {
        this.key = key;
        this.namespace = namespace;
    }

    public static MinecraftKey from(String s) {
        if (s.contains(":")) {
            String[] split = s.split(":");
            return new MinecraftKey(split[0], split[1]);
        } else {
            return new MinecraftKey(MINECRAFT, s);
        }
    }

    public static MinecraftKey from(NamespacedKey key) {
        return new MinecraftKey(key.getKey(), key.getNamespace());
    }

    public static MinecraftKey from(Material material) {
        return from(material.getKey());
    }

    public String getKey() {
        return key;
    }

    public String getNamespace() {
        return namespace;
    }
}
