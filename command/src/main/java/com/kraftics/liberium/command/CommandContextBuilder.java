package com.kraftics.liberium.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandContextBuilder {
    private final CommandSender sender;
    private final String input;
    private final Map<String, Object> arguments;
    private CommandNode parentNode;
    private CommandNode command;

    private CommandContextBuilder(CommandSender sender, String input, Map<String, Object> arguments, CommandNode command, CommandNode parentNode) {
        this.sender = sender;
        this.input = input;
        this.arguments = arguments;
        this.command = command;
        this.parentNode = parentNode;
    }

    public CommandContextBuilder(CommandSender sender, String input) {
        this(sender, input, new HashMap<>(), null, null);
    }

    public CommandContextBuilder withCommand(CommandNode node) {
        this.command = node;
        return this;
    }

    public CommandContextBuilder withArgument(String name, Object o) {
        this.arguments.put(name, o);
        return this;
    }

    public CommandContextBuilder withParent(CommandNode node) {
        this.parentNode = node;
        return this;
    }

    public CommandNode getParent() {
        return parentNode;
    }

    public CommandNode getCommand() {
        return command;
    }

    public CommandContextBuilder copy() {
        return new CommandContextBuilder(sender, input, arguments, command, parentNode);
    }

    public CommandContext build() {
        return new CommandContext(sender, input, arguments, command);
    }
}
