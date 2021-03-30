package com.kraftics.krafticslib.packet;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

/**
 * Represents an event for receiving and sending packets
 */
public class PacketEvent {
    private boolean cancelled = false;
    private final ObjectBuffer buffer;
    private final PacketType packetType;
    private final Player player;
    private final Channel channel;

    /**
     * Creates a new instance. (wow that's what constructors do)
     *
     * @param buffer The object buffer
     * @param packetType The packet type
     * @param player The player
     * @param channel The channel
     */
    public PacketEvent(ObjectBuffer buffer, PacketType packetType, Player player, Channel channel) {
        this.buffer = buffer;
        this.packetType = packetType;
        this.player = player;
        this.channel = channel;
    }

    /**
     * @return The object buffer
     */
    public ObjectBuffer getBuffer() {
        return buffer;
    }

    /**
     * @return The packet type
     */
    public PacketType getPacketType() {
        return packetType;
    }

    /**
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Sets if this event should be cancelled
     *
     * @param cancelled If should be cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return If this event is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }
}
