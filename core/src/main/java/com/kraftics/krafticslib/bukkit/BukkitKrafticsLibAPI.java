package com.kraftics.krafticslib.bukkit;

import com.kraftics.krafticslib.KrafticsLibAPI;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BukkitKrafticsLibAPI implements KrafticsLibAPI {
    private final Plugin plugin;
    private final CommandDispatcher<CommandSender> commandDispatcher;
    private final Reflection reflection;
    private final PacketProcessor packetProcessor;

    public BukkitKrafticsLibAPI(Plugin plugin) {
        this.plugin = plugin;
        this.commandDispatcher = new CommandDispatcher<>();
        this.reflection = new Reflection();
        this.packetProcessor = new PacketProcessor(this.reflection);
    }

    @Override
    public CommandDispatcher<CommandSender> getCommandDispatcher() {
        return commandDispatcher;
    }

    @Override
    public Reflection getReflection() {
        return reflection;
    }

    @Override
    public PacketProcessor getPacketProcessor() {
        return packetProcessor;
    }
}
