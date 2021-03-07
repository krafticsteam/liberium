package com.kraftics.krafticslib.bukkit;

import com.kraftics.krafticslib.KrafticsLibAPI;
import com.kraftics.krafticslib.command.CommandDispatcher;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class BukkitKrafticsLibAPI implements KrafticsLibAPI {
    private final Plugin plugin;
    private final Reflection reflection;
    private final PacketProcessor packetProcessor;
    private final CommandDispatcher commandDispatcher;

    public BukkitKrafticsLibAPI(Plugin plugin) {
        this.plugin = plugin;
        this.reflection = new Reflection();
        this.packetProcessor = new PacketProcessor(this.reflection);
        this.commandDispatcher = new CommandDispatcher(plugin.getName().toLowerCase(Locale.ROOT), getCommandMap());
    }

    @Override
    @NotNull
    public Reflection getReflection() {
        return reflection;
    }

    @Override
    @NotNull
    public PacketProcessor getPacketProcessor() {
        return packetProcessor;
    }

    @Override
    @NotNull
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    private CommandMap getCommandMap() {
        try {
            Class<?> craftServerClass = reflection.getCraftClass("CraftServer");
            if (craftServerClass == null) throw new IllegalStateException("Could not get craft server");
            Object craftServer = craftServerClass.cast(Bukkit.getServer());
            Method method = craftServerClass.getMethod("getCommandMap");
            return (CommandMap) method.invoke(craftServer);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not get command map", e);
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
