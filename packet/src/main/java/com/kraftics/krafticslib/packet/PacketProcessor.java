package com.kraftics.krafticslib.packet;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PacketProcessor {

    boolean sendPacket(@NotNull Player player, @NotNull Object packet);

    boolean sendPacket(@NotNull Player player, @NotNull ObjectBuffer packet);

    void addListener(PacketListener listener);

    List<PacketListener> getListeners();

    int getPing(Player player);

    Object getHandle(Player player);

    Object getPlayerConnection(Player player);

    void close();

    boolean isClosed();

    PacketRegistry getRegistry();

    ChannelInjector getChannelInjector();
}
