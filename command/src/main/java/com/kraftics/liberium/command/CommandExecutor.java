package com.kraftics.liberium.command;

import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandExecutor {

    void execute(CommandContext ctx) throws CommandSyntaxException;
}
