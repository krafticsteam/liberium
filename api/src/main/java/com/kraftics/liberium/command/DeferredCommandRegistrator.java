package com.kraftics.liberium.command;

import com.kraftics.liberium.nms.NetMinecraftServer;
import org.bukkit.plugin.Plugin;

public class DeferredCommandRegistrator implements CommandRegistrator {
    private final NetMinecraftServer nms;
    private final Plugin plugin;
    private final CommandManager commandManager;

    public DeferredCommandRegistrator(NetMinecraftServer nms, Plugin plugin, CommandManager commandManager) {
        this.nms = nms;
        this.plugin = plugin;
        this.commandManager = commandManager;
    }

    @Override
    public void register(LiberiumCommandNode node) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            this.nms.getCommandMap().register(plugin.getName(), new LiberiumCommand(node, commandManager));
            this.nms.registerConvertedCommand(node);
        });
    }
}
