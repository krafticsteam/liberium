package com.kraftics.krafticslib.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used when tab completing
 *
 * @see CommandContext
 * @author Panda885
 */
public class TabContext {
    private final CommandSender sender;
    private final String input;

    private final String remaining;
    private final int index;

    private final List<String> arguments;

    /**
     * Creates new tab context instance,
     * it's recommended to use {@link #create(CommandSender, String)}
     *
     * @param sender The sender who is executing this tab completion
     * @param input The raw input that user entered
     * @param remaining The last argument
     * @param index The index of last argument
     * @param arguments List of all arguments
     */
    public TabContext(@NotNull CommandSender sender, @NotNull String input, @Nullable String remaining, int index, @NotNull List<String> arguments) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(arguments, "Arguments cannot be null");

        this.sender = sender;
        this.input = input;
        this.remaining = remaining;
        this.index = index;
        this.arguments = arguments;
    }

    /**
     * Creates new {@link TabContext} from the raw input
     *
     * @param sender The sender who is executing this tab completion
     * @param input The raw user input
     * @return The tab contenxt
     */
    @NotNull
    public static TabContext create(@NotNull CommandSender sender, @NotNull String input) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");

        String[] split = input.split(" ", -1);
        if (split.length <= 1) return new TabContext(sender, input, null, -1, new ArrayList<>());
        List<String> arguments = Arrays.asList(split).subList(1, split.length);
        int index = arguments.size() - 1;
        return new TabContext(sender, input, arguments.get(index), index, arguments);
    }

    /**
     * Gets the sender who is executing this tab completion
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
     * Gets the last argument
     *
     * @return the argument, null if index is -1 (should not happen)
     */
    public String getRemaining() {
        return remaining;
    }

    /**
     * Gets the index of the last argument
     *
     * @return the index, can be -1 (should not happen)
     */
    public int getIndex() {
        return index;
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
     * Checks if this tab context contains an argument
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
}
