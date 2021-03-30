package com.kraftics.krafticslib.packet;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class PacketEvent {
    private boolean cancelled = false;
    private final ObjectBuffer buffer;
    private final PacketType packetType;
    private final Player player;
    private final Channel channel;

    public PacketEvent(ObjectBuffer buffer, PacketType packetType, Player player, Channel channel) {
        this.buffer = buffer;
        this.packetType = packetType;
        this.player = player;
        this.channel = channel;
    }

    public ObjectBuffer getBuffer() {
        return buffer;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public Player getPlayer() {
        return player;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
