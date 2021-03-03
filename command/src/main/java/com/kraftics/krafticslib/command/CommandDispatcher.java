package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher {
    private final CommandMap map;
    private final List<Command> commands = new ArrayList<>();

    /**
     * Creates a new command dispatcher
     *
     * @param map Bukkit's command map to register the commands
     */
    public CommandDispatcher(CommandMap map) {
        this.map = map;
    }

    /**
     * Registers a command to the dispatcher,
     * this will also registers the command to entered command map
     *
     * @param command the command to register
     */
    public void register(Command command) {
        this.commands.add(command);
        map.register(command.getName(), new LibCommand(command));
    }

    /**
     * Executes command that matches the input
     *
     * @param sender sender who is sending this command
     * @param input input of the command
     */
    public void execute(CommandSender sender, String input) {
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
    public List<String> tabComplete(CommandSender sender, String input) {
        String name = input.split(" ")[0];
        Command command = getCommand(name);
        if (command == null) return new ArrayList<>();
        return command.tabComplete(TabContext.create(sender, input));
    }

    /**
     * Gets a command by name
     *
     * @param name the name
     * @return the command
     */
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
    public List<Command> getCommands() {
        return commands;
    }
}
