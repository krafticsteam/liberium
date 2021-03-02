package com.kraftics.krafticslib.bukkit;

import com.kraftics.krafticslib.KrafticsLibAPI;
import com.kraftics.krafticslib.command.CommandDispatcher;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;
import org.bukkit.plugin.Plugin;

public class BukkitKrafticsLibAPI implements KrafticsLibAPI {
    private final Plugin plugin;
    private final Reflection reflection;
    private final PacketProcessor packetProcessor;
    private final CommandDispatcher commandDispatcher;

    public BukkitKrafticsLibAPI(Plugin plugin) {
        this.plugin = plugin;
        this.reflection = new Reflection();
        this.packetProcessor = new PacketProcessor(this.reflection);
        this.commandDispatcher = new CommandDispatcher();
    }

    @Override
    public Reflection getReflection() {
        return reflection;
    }

    @Override
    public PacketProcessor getPacketProcessor() {
        return packetProcessor;
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }
}
