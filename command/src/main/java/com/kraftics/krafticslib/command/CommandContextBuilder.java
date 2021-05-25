package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandContextBuilder {
    private final CommandSender sender;
    private final String input;
    private final Map<String, Object> arguments;
    private CommandNode command;

    private CommandContextBuilder(CommandSender sender, String input, Map<String, Object> arguments, CommandNode command) {
        this.sender = sender;
        this.input = input;
        this.arguments = arguments;
        this.command = command;
    }

    public CommandContextBuilder(CommandSender sender, String input) {
        this(sender, input, new HashMap<>(), null);
    }

    public CommandContextBuilder withCommand(CommandNode node) {
        this.command = node;
        return this;
    }

    public CommandContextBuilder withArgument(String name, Object o) {
        this.arguments.put(name, o);
        return this;
    }

    public CommandNode getCommand() {
        return command;
    }

    public CommandContextBuilder copy() {
        return new CommandContextBuilder(sender, input, arguments, command);
    }

    public CommandContext build() {
        return new CommandContext(sender, input, arguments, command);
    }
}
