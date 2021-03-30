package com.kraftics.krafticslib.bukkit;

import com.kraftics.krafticslib.KrafticsLibAPI;
import com.kraftics.krafticslib.command.CommandDispatcher;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.bukkit.BukkitPacketProcessor;
import com.kraftics.krafticslib.packet.reflection.MethodInvoker;
import com.kraftics.krafticslib.packet.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BukkitKrafticsLibAPI implements KrafticsLibAPI {
    private final Plugin plugin;
    private final PacketProcessor packetProcessor;
    private final CommandDispatcher commandDispatcher;

    public BukkitKrafticsLibAPI(Plugin plugin) {
        this.plugin = plugin;
        this.packetProcessor = new BukkitPacketProcessor(plugin);
        this.commandDispatcher = new CommandDispatcher(plugin.getName().toLowerCase(Locale.ROOT), getCommandMap());
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
        Class<?> craftServerClass = Reflection.getCraftClass("CraftServer");
        Object craftServer = craftServerClass.cast(Bukkit.getServer());
        MethodInvoker<CommandMap> method = Reflection.getMethod(craftServerClass, "getCommandMap", CommandMap.class);
        return method.invoke(craftServer);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
