package com.kraftics.liberium.command;

import com.kraftics.liberium.command.argument.Argument;
import com.kraftics.liberium.command.argument.ArgumentCommandNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used to build command nodes easily
 *
 * @author Panda885
 */
public class CommandBuilder {
    private final String name;
    private final List<ArgumentBuilder> arguments = new LinkedList<>();
    private String description;
    private String[] aliases = new String[0];
    private CommandExecutor executor;

    /**
     * Creates a new command builder.
     *
     * @param name the name of the command
     */
    public CommandBuilder(String name) {
        this.name = name;
    }

    /**
     * Sets description for this command
     *
     * @param description the description
     * @return this command builder
     */
    public CommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets aliases for this command
     *
     * @param aliases the aliases
     * @return this command builder
     */
    public CommandBuilder setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * Adds an executor without any arguments
     *
     * @param executor the executor
     * @return this command builder
     */
    public CommandBuilder execute(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Adds an executor with some arguments in order
     *
     * @param executor the executor
     * @param arguments array of the arguments
     * @return this command builder
     */
    public CommandBuilder execute(CommandExecutor executor, Argument<?>... arguments) {
        return execute(executor, Arrays.asList(arguments));
    }

    /**
     * Adds an executor with some arguments in order
     *
     * @param executor the executor
     * @param list list of the arguments
     * @return this command builder
     */
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

    private static ArgumentBuilder getNode(Argument<?> argument, List<ArgumentBuilder> childs) {
        for (ArgumentBuilder node : childs) {
            if (node.argument == argument) {
                return node;
            }
        }
        return null;
    }

    private List<ArgumentBuilder> getArguments(ArgumentBuilder current) {
        return current == null ? arguments : current.arguments;
    }

    /**
     * Builds a command node
     *
     * @return root command node built with this builder
     */
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
