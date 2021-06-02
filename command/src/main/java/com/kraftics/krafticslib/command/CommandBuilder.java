package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.argument.Argument;
import com.kraftics.krafticslib.command.argument.ArgumentCommandNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBuilder {
    private final String name;
    private final List<ArgumentBuilder> arguments = new ArrayList<>();
    private String description;
    private String[] aliases = new String[0];
    private CommandExecutor executor;

    public CommandBuilder(String name) {
        this.name = name;
    }

    public CommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder execute(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public CommandBuilder execute(CommandExecutor executor, Argument<?>... arguments) {
        return execute(executor, Arrays.asList(arguments));
    }

    public CommandBuilder execute(CommandExecutor executor, List<Argument<?>> list) {
        ArgumentBuilder current = null;
        for (int i = 0; i < list.size(); i++) {
            Argument<?> argument = list.get(i);

            ArgumentBuilder node = getNode(argument, getArguments(current));
            if (node == null) {
                node = new ArgumentBuilder(argument);
                getArguments(current).add(node);
            }

            current = node;
            if (i == list.size() - 1) {
                node.executor = executor;
            }
        }
        return this;
    }

    private List<ArgumentBuilder> getArguments(ArgumentBuilder current) {
        return current == null ? arguments : current.arguments;
    }

    public RootCommandNode build() {
        return new RootCommandNode(name, description, aliases, executor, buildNodes(arguments));
    }

    private static List<CommandNode> buildNodes(List<ArgumentBuilder> arguments) {
        List<CommandNode> nodes = new ArrayList<>();
        for (ArgumentBuilder builder : arguments) {
            nodes.add(new ArgumentCommandNode(builder.executor, buildNodes(builder.arguments), builder.argument));
        }
        return nodes;
    }

    private static ArgumentBuilder getNode(Argument<?> argument, List<ArgumentBuilder> childs) {
        for (ArgumentBuilder node : childs) {
            if (node.argument.getName().equals(argument.getName())) {
                return node;
            }
        }
        return null;
    }

    private static class ArgumentBuilder {
        public final Argument<?> argument;
        public final List<ArgumentBuilder> arguments;
        public CommandExecutor executor;

        public ArgumentBuilder(Argument<?> argument) {
            this.argument = argument;
            this.arguments = new ArrayList<>();
            this.executor = null;
        }
    }
}
