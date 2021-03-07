package com.kraftics.krafticslib.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher {
    private final CommandMap map;
    private final List<Command> commands = new ArrayList<>();
    private final String prefix;

    /**
     * Creates a new command dispatcher
     *
     * @param prefix The prefix to use for registering commands - {@code /prefix:command}
     * @param map Bukkit's command map to register the commands
     */
    public CommandDispatcher(@NotNull String prefix, @NotNull CommandMap map) {
        Validate.notNull(prefix, "Prefix cannot be null");
        Validate.notNull(map, "Map cannot be null");

        this.map = map;
        this.prefix = prefix;
    }

    /**
     * Registers a command to the dispatcher,
     * this will also register the command to entered command map
     *
     * @param command the command to register
     */
    public void register(@NotNull Command command) {
        register(prefix, command);
    }

    /**
     * Register a command to the dispatcher,
     * this will also register the command to entered command map
     *
     * @param prefix the prefix of the command - {@code /prefix:command}
     * @param command the commnad to register
     */
    public void register(@NotNull String prefix, @Nullable Command command) {
        this.commands.add(command);
        map.register(prefix, new LibCommand(command));
    }

    /**
     * Executes command that matches the input
     *
     * @param sender sender who is sending this command
     * @param input input of the command
     */
    public void execute(@NotNull CommandSender sender, @NotNull String input) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");

        String name = input.split(" ")[0];
        Command command = getCommand(name);
        if (command == null) return;
        command.execute(CommandContext.create(sender, input));
    }

    /**
     * Gets suggestions for the entered input
     *
     * @param sender sender who is sending this command
     * @param input the input
     * @return the suggestions
     */
    @Nullable
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String input) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(input, "Input cannot be null");

        String name = input.split(" ")[0];
        Command command = getCommand(name);
        if (command == null) return new ArrayList<>();
        return command.tabComplete(TabContext.create(sender, input));
    }

    /**
     * Gets a command by name
     *
     * @param name the name
     * @return the command, null if not found
     */
    @Nullable
    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    /**
     * Gets all commands registered in this dispatcher
     *
     * @return the commands
     */
    @NotNull
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Gets prefix of this command dispatcher - {@code /prefix:command}
     *
     * @return the prefix
     */
    @NotNull
    public String getPrefix() {
        return prefix;
    }
}
