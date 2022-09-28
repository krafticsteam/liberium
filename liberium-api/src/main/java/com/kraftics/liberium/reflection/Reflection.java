package com.kraftics.liberium.reflection;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Reflection {
    protected static final String VERSION = detectVersion();

    @NotNull
    public static String detectVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Could not detect version of this server", e);
        }
    }

    @NotNull
    public static Optional<ClassWrapper> getClass(@NotNull String path) {
        try {
            return Optional.of(ClassWrapper.wrap(Class.forName(path)));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    @NotNull
    public static Optional<ClassWrapper> getNetMinecraftClass(@NotNull String path) {
        return getClass("net.minecraft." + path);
    }

    @NotNull
    public static Optional<ClassWrapper> getNetMinecraftServerClass(@NotNull String path) {
        return getClass("net.minecraft.server." + path);
    }

    @NotNull
    public static Optional<ClassWrapper> getCraftClass(@NotNull String path) {
        return getClass("org.bukkit.craftbukkit." + VERSION + '.' + path);
    }
}
