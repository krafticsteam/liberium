package com.kraftics.liberium.packet;

import io.netty.channel.Channel;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * This class is used to inject packet listeners to a channel
 */
public interface ChannelInjector {

    /**
     * Injects all players on a server
     *
     * @param server The server
     */
    default void injectAll(Server server) {
        for (Player player : server.getOnlinePlayers()) {
            inject(player);
        }
    }

    /**
     * Injects to a player
     *
     * @param player The player
     */
    default void inject(Player player) {
        inject(getChannel(player));
    }

    /**
     * Injects to a channel
     *
     * @param channel The channel
     */
    void inject(Channel channel);

    /**
     * Uninjects from a player
     *
     * @param player The player
     */
    default void uninject(Player player) {
        uninject(getChannel(player));
    }

    /**
     * Uninjects from a channel
     *
     * @param channel The channel
     */
    void uninject(Channel channel);

    /**
     * Checks if a player is injected
     *
     * @param player The player
     * @return If a player is injected
     */
    default boolean isInjected(Player player) {
        return isInjected(getChannel(player));
    }

    /**
     * Checks if a channel is injected
     *
     * @param channel The channel
     * @return If a channel is injected
     */
    boolean isInjected(Channel channel);

    /**
     * Gets a channel from a player
     *
     * @param player The player
     * @return The channel
     */
    Channel getChannel(Player player);

    /**
     * Name of this channel injector
     *
     * @return The name
     */
    String getName();
}
