package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher {
    private List<Command> commands = new ArrayList<>();

    public void register(Command command) {
        this.commands.add(command);
    }

    public void registerTo(CommandMap map) {
        for (Command command : commands) {
            map.register(command.getName(), new LibCommand(command));
        }
    }

    public void execute(CommandSender sender, String input) {
        String name = input.split(" ")[0];
        Command command = getCommand(name);
        command.execute(CommandContext.create(sender, input));
    }

    public List<String> tabComplete(CommandSender sender, String input) {
        String name = input.split(" ")[0];
        Command command = getCommand(name);
        return command.tabComplete(TabContext.create(sender, input));
    }

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
