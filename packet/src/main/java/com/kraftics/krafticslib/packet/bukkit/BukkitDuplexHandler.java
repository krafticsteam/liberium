package com.kraftics.krafticslib.packet.bukkit;

import com.kraftics.krafticslib.packet.ObjectBuffer;
import com.kraftics.krafticslib.packet.PacketEvent;
import com.kraftics.krafticslib.packet.PacketType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class BukkitDuplexHandler extends ChannelDuplexHandler {
    private final BukkitPacketProcessor processor;
    protected Player player;

    public BukkitDuplexHandler(BukkitPacketProcessor processor, @Nullable Player player) {
        this.processor = processor;
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();

        PacketType packetType = processor.getRegistry().get(msg);
        if (packetType == null) {
            super.channelRead(ctx, msg);
            return;
        }

        ObjectBuffer buffer = new ObjectBuffer(msg);
        PacketEvent event = new PacketEvent(buffer, packetType, player, channel);

        processor.sendPacketReceivingEvent(event);

        if (!event.isCancelled()) {
            super.channelRead(ctx, event.getBuffer().getObject());
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Channel channel = ctx.channel();

        PacketType packetType = processor.getRegistry().get(msg);
        if (packetType == null) {
            super.write(ctx, msg, promise);
            return;
        }

        ObjectBuffer buffer = new ObjectBuffer(msg);
        PacketEvent event = new PacketEvent(buffer, packetType, player, channel);

        processor.sendPacketSendingEvent(event);

        if (!event.isCancelled()) {
            super.write(ctx, event.getBuffer().getObject(), promise);
        }
    }

    public BukkitPacketProcessor getProcessor() {
        return processor;
    }

    @Nullable
    public Player getPlayer() {
        return player;
    }
}
