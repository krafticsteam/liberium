package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.*;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

import java.util.List;

public class ArgumentCommandNode implements CommandNode {
    private final CommandExecutor executor;
    private final List<CommandNode> children;
    private final Argument<?> argument;

    public ArgumentCommandNode(CommandExecutor executor, List<CommandNode> children, Argument<?> argument) {
        this.executor = executor;
        this.children = children;
        this.argument = argument;
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
        context.withArgument(argument.getName(), argument.parse(reader));
    }

    @Override
    public List<String> tabComplete(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException {
        return argument.tabComplete(reader);
    }

    public Argument<?> getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return "ArgumentCommandNode{" +
                "executor=" + executor +
                ", children=" + children +
                ", argument=" + argument +
                '}';
    }
}
