package com.kraftics.liberium.bukkit;

import com.kraftics.liberium.LiberiumAPI;
import com.kraftics.liberium.command.CommandDispatcher;
import com.kraftics.liberium.packet.PacketProcessor;
import com.kraftics.liberium.packet.bukkit.BukkitPacketProcessor;
import com.kraftics.liberium.packet.reflection.MethodInvoker;
import com.kraftics.liberium.packet.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BukkitLiberiumAPI implements LiberiumAPI {
    private final Plugin plugin;
    private final PacketProcessor packetProcessor;
    private final CommandDispatcher commandDispatcher;

    public BukkitLiberiumAPI(Plugin plugin) {
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
