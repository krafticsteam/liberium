package com.kraftics.liberium.packet.bukkit;

import com.kraftics.liberium.packet.ChannelInjector;
import com.kraftics.liberium.packet.reflection.FieldAccessor;
import com.kraftics.liberium.packet.reflection.Reflection;
import com.kraftics.liberium.packet.reflection.ReflectionUtil;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class BukkitChannelInjector implements ChannelInjector {
    private final BukkitPacketProcessor processor;
    private final String name;

    private final FieldAccessor<Object> networkManagerField;
    private final FieldAccessor<Channel> channelField;

    public BukkitChannelInjector(String name, BukkitPacketProcessor processor) {
        this.name = name;
        this.processor = processor;

        this.networkManagerField = Reflection.getField(ReflectionUtil.getPlayerConnectionClass(), "networkManager", Object.class);
        this.channelField = Reflection.getField(Reflection.getNMSClass("NetworkManager"), Channel.class, 0);
    }

    @Override
    public void inject(Player player) {
        injectInternal(getChannel(player)).player = player;
    }

    @Override
    public void inject(Channel channel) {
        injectInternal(channel);
    }

    private BukkitDuplexHandler injectInternal(Channel channel) {
        BukkitDuplexHandler handler = (BukkitDuplexHandler) channel.pipeline().get(name);

        if (handler == null) {
            handler = new BukkitDuplexHandler(processor, null);
            channel.pipeline().addBefore("packet_handler", name, handler);
        }

        return handler;
    }

    @Override
    public void uninject(Channel channel) {
        if (!isInjected(channel)) return;
        channel.eventLoop().execute(() -> channel.pipeline().remove(name));
    }

    @Override
    public boolean isInjected(Channel channel) {
        return channel.pipeline().get(name) != null;
    }

    @Override
    public Channel getChannel(Player player) {
        Object playerConnection = processor.getPlayerConnection(player);
        Object networkManager = networkManagerField.get(playerConnection);
        return channelField.get(networkManager);
    }

    @Override
    public String getName() {
        return name;
    }
}
