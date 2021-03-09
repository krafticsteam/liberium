package com.kraftics.krafticslib.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used when executing a command
 *
 * @author Panda885
 */
public class CommandContext {
    private final CommandSender sender;
    private final String input;

    private final List<String> arguments;
    private final List<String> flags;

    /**
     * Creates new command context instance,
     * it's recommended to use {@link #create(CommandSender, String)}
     *
     * @param sender The sender who is executing this command
     * @param input The raw input that the user entered
     * @param arguments List of all arguments
     * @param flags List of all flags
     */
    public CommandContext(@NotNull CommandSender sender, @NotNull String input, @NotNull List<String> arguments, @NotNull List<String> flags) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(arguments, "Arguments cannot be null");
        Validate.notNull(flags, "Flags cannot be null");

        this.sender = sender;
        this.input = input;
        this.arguments = arguments;
        this.flags = flags;
    }

    /**
     * Creates new {@link CommandContext} from the raw input
     *
     * @param sender The sender who is executing this command
     * @param message The raw user input
     * @return The command context
     */
    @NotNull
    public static CommandContext create(@NotNull CommandSender sender, @NotNull String message) {
        Validate.notNull(sender, "Sender cannot be null");

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
     * Gets list of all arguments
     *
     * @return the arguments
     */
    @NotNull
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Checks if this command context contains an argument
     *
     * @param s The argument content
     * @return true = contains, false = does not contain
     */
    public boolean hasArgument(String s) {
        return arguments.contains(s);
    }

    /**
     * Gets argument from an index
     *
     * @param i the index
     * @return the argument, null if out of bounds
     */
    public String getArgument(int i) {
        if (i < 0 || i >= arguments.size()) return null;
        return arguments.get(i);
    }

    /**
     * Checks if this command context contains a flag
     *
     * @param s The flag content
     * @return true = contains, false = does not contain
     */
    public boolean hasFlag(String s) {
        return flags.contains(s);
    }

    /**
     * Gets flag from an index
     *
     * @param i the index
     * @return the flag, null if out of bounds
     */
    public String getFlag(int i) {
        if (i < 0 || i >= flags.size()) return null;
        return flags.get(i);
    }

    /**
     * Gets list of all flags
     *
     * @return the flags
     */
    @NotNull
    public List<String> getFlags() {
        return flags;
    }
}
