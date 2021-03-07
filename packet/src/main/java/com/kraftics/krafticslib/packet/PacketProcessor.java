package com.kraftics.krafticslib.packet;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Packet handler for NMS and CraftBukkit
 * <p>
 * This class is mainly used to send packets, but it can also be used to retreive ping or other things.
 * </p>
 *
 * @author Kraftics
 * @since 1.0.0
 * @see Reflection
 */
public class PacketProcessor {
    private final Reflection reflection;

    private final MethodHandle getHandle;
    private final MethodHandle playerConnection;
    private final MethodHandle sendPacket;
    private final MethodHandle ping;

    /**
     * Creates instance of the packet processor.
     *
     * @param reflection Reflection this packet processor should use.
     * @throws IllegalArgumentException If the reflection is invalid.
     */
    public PacketProcessor(@NotNull Reflection reflection) throws IllegalArgumentException {
        Validate.notNull(reflection, "Reflection cannot be null");

        this.reflection = reflection;

        try {
            Class<?> entityPlayerClass = reflection.getNMSClass("EntityPlayer");
            Class<?> craftPlayerClass = reflection.getCraftClass("entity.CraftPlayer");
            Class<?> playerConnectionClass = reflection.getNMSClass("PlayerConnection");

            MethodHandles.Lookup lookup = MethodHandles.lookup();

            getHandle = lookup.findVirtual(craftPlayerClass, "getHandle", MethodType.methodType(entityPlayerClass));
            playerConnection = lookup.findGetter(entityPlayerClass, "playerConnection", playerConnectionClass);
            sendPacket = lookup.findVirtual(playerConnectionClass, "sendPacket", MethodType.methodType(void.class, reflection.getNMSClass("Packet")));
            ping = lookup.findGetter(entityPlayerClass, "ping", int.class);
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid reflection", e);
        }
    }

    /**
     * Sends packet to a player
     *
     * @param player the player
     * @param packet the packet
     * @return if the packet was successfully sent
     */
    public boolean sendPacket(@NotNull Player player, @NotNull Object packet) {
        Validate.notNull(player, "Player cannot be null");
        Validate.notNull(packet, "Packet cannot be null");

        try {
            Object connection = getPlayerConnection(player);
            if (connection == null) return false;
            sendPacket.invoke(connection, packet);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets player connection of a player used to send packets or other things.
     *
     * @throws IllegalStateException if something went wrong
     * @param player the player
     * @return the connection, possibly null
     */
    public Object getPlayerConnection(@NotNull Player player) throws IllegalStateException {
        Validate.notNull(player, "Player cannot be null");

        try {
            Object handle = getHandle.invoke(player);
            return playerConnection.invoke(handle);
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }
    }

    /**
     * Gets ping of a player
     *
     * @throws IllegalStateException if something went wrong
     * @param player the player
     * @return the ping
     */
    public int getPing(@NotNull Player player) {
        Validate.notNull(player, "Player cannot be null");

        try {
            Object handle = getHandle.invoke(player);
            return (int) ping.invoke(handle);
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }
    }

    public MethodHandle getGetHandleHandle() {
        return getHandle;
    }

    public MethodHandle getPlayerConnectionHandle() {
        return playerConnection;
    }

    public MethodHandle getSendPacketHandle() {
        return sendPacket;
    }

    public MethodHandle getPingHandle() {
        return ping;
    }

    public Reflection getReflection() {
        return reflection;
    }
}
