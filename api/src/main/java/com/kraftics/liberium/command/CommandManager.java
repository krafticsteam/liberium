package com.kraftics.liberium.command;

import com.kraftics.liberium.LiberiumPlugin;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;

public class CommandManager {
    private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();
    private final CommandRegistrator registrator;

    public CommandManager(LiberiumPlugin plugin) {
        this.registrator = new DeferredCommandRegistrator(plugin.getNetMinecraftServer(), plugin, dispatcher);
    }

    public void register(CommandArgumentBuilder command) {
        LiberiumCommandNode node = command.build();
        this.dispatcher.getRoot().addChild(node);
        registrator.register(node);
    }
}
