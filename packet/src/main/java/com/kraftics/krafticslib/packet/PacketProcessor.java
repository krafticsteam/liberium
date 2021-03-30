package com.kraftics.krafticslib.packet;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class is used to manage packets and other things around networks
 */
public interface PacketProcessor {

    /**
     * Sends packet to a player
     *
     * @param player The player
     * @param packet The packet
     * @return If the packet was successfully sent
     */
    boolean sendPacket(@NotNull Player player, @NotNull Object packet);

    /**
     * Sends packet to a player
     *
     * @param player The player
     * @param buffer The {@link ObjectBuffer}
     * @return If the packet was successfully sent
     */
    boolean sendPacket(@NotNull Player player, @NotNull ObjectBuffer buffer);

    /**
     * Adds a listener
     *
     * @param listener the listener
     */
    void addListener(PacketListener listener);

    /**
     * Gets registered listeners in this PacketProcessor
     *
     * @return The listeners
     */
    List<PacketListener> getListeners();

    /**
     * Gets ping of a player
     *
     * @param player The player
     * @return The ping
     */
    int getPing(Player player);

    /**
     * Gets a player nms handle (EntityPlayer)
     *
     * @param player The player
     * @return The handle
     */
    Object getHandle(Player player);

    /**
     * Gets a player nms connection (PlayerConnection)
     *
     * @param player The player
     * @return The player connection
     */
    Object getPlayerConnection(Player player);

    /**
     * Closes this PacketProcessor.
     * <p>
     * This will uninject players and block {@link #sendPacket} methods
     * </p>
     */
    void close();

    /**
     * @return If this PacketProcessor is closed
     */
    boolean isClosed();

    /**
     * @return The packet registry
     */
    PacketRegistry getRegistry();

    /**
     * @return The channel injector
     */
    ChannelInjector getChannelInjector();
}
