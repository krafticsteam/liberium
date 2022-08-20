package com.kraftics.liberium.command;

import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;

public class CommandManager {
    private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();

    // TODO: Add checks for command already existing
    public void registerCommand(CommandArgumentBuilder command) {
        this.dispatcher.register(command);
    }
}
