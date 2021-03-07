package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandContext {
    private final CommandSender sender;
    private final String input;

    private final List<String> arguments;
    private final List<String> flags;

    public CommandContext(CommandSender sender, String input, List<String> arguments, List<String> flags) {
        this.sender = sender;
        this.input = input;
        this.arguments = arguments;
        this.flags = flags;
    }

    public static CommandContext create(CommandSender sender, String message) {
        String[] split = message.split(" ", -1);

        if (split.length <= 1) return new CommandContext(sender, message, Collections.emptyList(), Collections.emptyList());

        List<String> flags = new ArrayList<>();
        List<String> args = new ArrayList<>();

        for (int i = 1; i < split.length; i++) {
            String argument = split[i];
            if (argument.startsWith("-")) {
                flags.add(argument.substring(1));
            } else {
                args.add(argument);
            }
        }

        return new CommandContext(sender, message, args, flags);
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getInput() {
        return input;
    }

    public boolean hasArgument(String s) {
        return arguments.contains(s);
    }

    public String getArgument(int i) {
        return arguments.get(i);
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean hasFlag(String s) {
        return flags.contains(s);
    }

    public String getFlag(int i) {
        return flags.get(i);
    }

    public List<String> getFlags() {
        return flags;
    }
}
