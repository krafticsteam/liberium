package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.List;

public final class RootCommandNode implements CommandNode {
    private final String name;
    private final String description;
    private final String[] aliases;
    private final List<CommandNode> children;
    private final CommandExecutor executor;

    public RootCommandNode(String name, String description, String[] aliases, CommandExecutor executor, List<CommandNode> children) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.children = children;
        this.executor = executor;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }

    @Override
    public List<CommandNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(CommandNode node) {
        if (node.getClass() == RootCommandNode.class) throw new IllegalArgumentException("Invalid node");

        children.add(node);
    }

    @Override
    public void parse(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException {
        String command = reader.readUnquotedString();
        if (command.equals(name)) return;
        throw CommandSyntaxException.BuiltIn.INVALID_COMMAND.build(reader);
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RootCommandNode{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", aliases=" + Arrays.toString(aliases) +
                ", children=" + children +
                ", executor=" + executor +
                '}';
    }
}
