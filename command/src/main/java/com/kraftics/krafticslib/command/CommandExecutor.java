package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandExecutor {

    void execute(CommandContext ctx) throws CommandSyntaxException;
}
