package com.kraftics.krafticslib.packet;

import org.bukkit.Bukkit;

/**
 * Reflection handler for NMS and CraftBukkit.
 * <p>
 * This class is used to get classes that contains version in their package names.
 * It's easy to use and straightforward. This class is also used in {@link PacketProcessor} to send packets.
 * </p>
 *
 * @author Kraftics
 * @since 1.0.0
 */
public class Reflection {
    private String version;

    /**
     * Creates an instance with the entered version
     *
     * @param version the version for the reflection
     */
    public Reflection(String version) {
        this.version = version;
    }

    /**
     * Creates an instace by detecting the version
     *
     * @throws IllegalStateException if the version could not be detected
     */
    public Reflection() throws IllegalStateException {
        this(detectVersion());
    }

    /**
     * Detects the version of this server for NMS or CraftBukkit classes
     *
     * @throws IllegalStateException if the version could not be detected
     * @return the detected version
     */
    public static String detectVersion() throws IllegalStateException {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Could not detect version using this server", e);
        }
    }

    /**
     * Gets a NMS (net.minecraft.server) class
     *
     * @param path path (name) of the class
     * @return the NMS class, null if not found
     */
    public Class<?> getNMSClass(String path) {
        try {
            return Class.forName("net.minecraft.server." + version + '.' + path);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets a CraftBukkit (org.bukkit.craftbukkit) class
     *
     * @param path path (name) of the class
     * @return the CraftBukkit class, null if not found
     */
    public Class<?> getCraftClass(String path) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + '.' + path);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
