package com.kraftics.liberium.packet.bukkit;

import com.kraftics.liberium.packet.*;
import com.kraftics.liberium.packet.reflection.FieldAccessor;
import com.kraftics.liberium.packet.reflection.MethodInvoker;
import com.kraftics.liberium.packet.reflection.Reflection;
import com.kraftics.liberium.packet.reflection.ReflectionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BukkitPacketProcessor implements PacketProcessor {
    private final Plugin plugin;
    private final BukkitChannelInjector channelInjector;

    private final LoginListener listener;
    private final List<PacketListener> listeners;

    private final MethodInvoker<Object> getHandleMethod;
    private final MethodInvoker<Void> sendPacketMethod;
    private final FieldAccessor<Object> playerConnectionField;
    private final FieldAccessor<Integer> pingField;

    private boolean closed;

    public BukkitPacketProcessor(Plugin plugin) {
        this.plugin = plugin;
        this.channelInjector = new BukkitChannelInjector("kl-" + plugin.getName(), this);

        this.listener = new LoginListener();
        this.listeners = new ArrayList<>();
        this.closed = false;

        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        getHandleMethod = ReflectionUtil.getPlayerHandleMethod();
        sendPacketMethod = Reflection.getMethod(ReflectionUtil.getPlayerConnectionClass(), "sendPacket", void.class, ReflectionUtil.getPacketClass());
        playerConnectionField = ReflectionUtil.getPlayerConnectionField();
        pingField = Reflection.getField(ReflectionUtil.getNmsPlayerClass(), "ping", int.class);
    }

    @Override
    public boolean sendPacket(@NotNull Player player, @NotNull Object packet) {
        if (closed) throw new IllegalStateException("This processor is closed");

        sendPacketMethod.invoke(getPlayerConnection(player), packet);
        return true;
    }

    @Override
    public boolean sendPacket(@NotNull Player player, @NotNull ObjectBuffer buffer) {
        if (closed) throw new IllegalStateException("This processor is closed");

        return sendPacket(player, buffer.getObject());
    }

    @Override
    public void addListener(PacketListener listener) {
        listeners.add(listener);
    }

    @Override
    public List<PacketListener> getListeners() {
        return listeners;
    }

    @Override
    public int getPing(Player player) {
        Integer ping = pingField.get(getHandle(player));
        if (ping == null) return 0;
        return ping;
    }

    @Override
    public Object getPlayerConnection(Player player) {
        return playerConnectionField.get(getHandle(player));
    }

    @Override
    public Object getHandle(Player player) {
        return getHandleMethod.invoke(player);
    }

    @Override
    public synchronized void close() {
        if (closed) return;
        this.closed = true;

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            channelInjector.uninject(player);
        }

        HandlerList.unregisterAll(listener);
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public ChannelInjector getChannelInjector() {
        return channelInjector;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    protected void sendPacketSendingEvent(PacketEvent event) {
        listeners.forEach(listener -> listener.onPacketSending(event));
    }

    protected void sendPacketReceivingEvent(PacketEvent event) {
        listeners.forEach(listener -> listener.onPacketReceiving(event));
    }

    public final class LoginListener implements Listener {

        @EventHandler
        public void onJoin(PlayerJoinEvent event) {
            channelInjector.inject(event.getPlayer());
        }

        @EventHandler
        public void onDisable(PluginDisableEvent event) {
            if (!event.getPlugin().equals(plugin)) return;

            close();
        }
    }
}
