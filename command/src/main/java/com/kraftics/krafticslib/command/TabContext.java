package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabContext {
    private final CommandSender sender;
    private final String input;

    private final String remaining;
    private final int index;

    private final List<String> arguments;

    public TabContext(CommandSender sender, String input, String remaining, int index, List<String> arguments) {
        this.sender = sender;
        this.input = input;
        this.remaining = remaining;
        this.index = index;
        this.arguments = arguments;
    }

    public static TabContext create(CommandSender sender, String input) {
        String[] split = input.split(" ");
        List<String> arguments = Arrays.asList(split).subList(1, split.length);
        int index = arguments.size() - 1;
        return new TabContext(sender, input, arguments.get(index), index, arguments);
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getInput() {
        return input;
    }

    public String getRemaining() {
        return remaining;
    }

    public int getIndex() {
        return index;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean hasArgument(String s) {
        return arguments.contains(s);
    }

    public String getArgument(int i) {
        return arguments.get(i);
    }
}
