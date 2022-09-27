package com.kraftics.liberium.command;

import com.kraftics.liberium.nms.NetMinecraftServer;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class DeferredCommandRegistrator implements CommandRegistrator {
    private final NetMinecraftServer nms;
    private final Plugin plugin;
    private final CommandDispatcher<CommandSender> dispatcher;

    public DeferredCommandRegistrator(NetMinecraftServer nms, Plugin plugin, CommandDispatcher<CommandSender> dispatcher) {
        this.nms = nms;
        this.plugin = plugin;
        this.dispatcher = dispatcher;
    }

    @Override
    public void register(LiberiumCommandNode node) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            this.nms.getCommandMap().register(plugin.getName(), new LiberiumCommand(node, dispatcher));
            this.nms.registerConvertedCommand(node);
        });
    }
}
