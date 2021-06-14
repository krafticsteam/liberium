package com.kraftics.liberium.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * This class is used when executing a command
 *
 * @author Panda885
 */
public class CommandContext {
    private final CommandSender sender;
    private final String input;
    private final Map<String, Object> arguments;
    private final CommandNode command;

    /**
     * Creates new command context instance
     *
     * @param sender The sender who is executing this command
     * @param input The raw input that the user entered
     * @param arguments Map of arguments
     * @param command The command node
     */
    public CommandContext(@NotNull CommandSender sender, @NotNull String input, @NotNull Map<String, Object> arguments, @NotNull CommandNode command) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(arguments, "Arguments cannot be null");
        Validate.notNull(command, "Command cannot be null");

        this.sender = sender;
        this.input = input;
        this.arguments = arguments;
        this.command = command;
    }

    /**
     * Gets the sender who is executing this command
     *
     * @return the sender
     */
    @NotNull
    public CommandSender getSender() {
        return sender;
    }


    /**
     * Gets the raw input that the user entered
     *
     * @return the input
     */
    @NotNull
    public String getInput() {
        return input;
    }

    /**
     * Gets the command node
     *
     * @return the command node
     */
    @NotNull
    public CommandNode getCommand() {
        return command;
    }

    /**
     * Gets map of arguments
     *
     * @return the arguments
     */
    public Map<String, Object> getArguments() {
        return arguments;
    }

    /**
     * Gets argument object by name
     *
     * @param name The name of the argument
     * @return The argument
     */
    public Object getArgument(String name) {
        return arguments.get(name);
    }

    /**
     * Gets argument object by name
     *
     * @param type The type of the argument
     * @param name The name of the argument
     * @param <T> The type of the argument
     * @return The argument
     */
    public <T> T getArgument(Class<T> type, String name) {
        Object argument = getArgument(name);
        if (argument == null) return null;
        return type.cast(argument);
    }

    /**
     * Checks if this command context contains an argument
     *
     * @param s The argument content
     * @return true = contains, false = does not contain
     */
    public boolean hasArgument(String s) {
        return arguments.containsKey(s);
    }
}
