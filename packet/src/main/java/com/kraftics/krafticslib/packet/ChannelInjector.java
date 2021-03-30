package com.kraftics.krafticslib.packet;

import io.netty.channel.Channel;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public interface ChannelInjector {

    default void injectAll(Server server) {
        for (Player player : server.getOnlinePlayers()) {
            inject(player);
        }
    }

    default void inject(Player player) {
        inject(getChannel(player));
    }

    void inject(Channel channel);

    default void uninject(Player player) {
        uninject(getChannel(player));
    }

    void uninject(Channel channel);

    default boolean isInjected(Player player) {
        return isInjected(getChannel(player));
    }

    boolean isInjected(Channel channel);

    Channel getChannel(Player player);

    String getName();
}
