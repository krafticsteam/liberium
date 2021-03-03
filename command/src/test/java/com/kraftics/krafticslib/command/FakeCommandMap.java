package com.kraftics.krafticslib.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeCommandMap implements CommandMap {
    private Map<String, Command> map = new HashMap<>();

    @Override
    public void registerAll(String fallbackPrefix, List<Command> commands) {
        commands.forEach(cmd -> register(fallbackPrefix, cmd));
    }

    @Override
    public boolean register(String label, String fallbackPrefix, Command command) {
        return register(fallbackPrefix, command);
    }

    @Override
    public boolean register(String fallbackPrefix, Command command) {
        map.put(fallbackPrefix, command);
        return true;
    }

    @Override
    public boolean dispatch(CommandSender sender, String input) throws CommandException {
        String name = input.split(" ")[0];
        Command command = getCommand(name);
        if (command == null) return true;
        command.execute(sender, input, new String[0]);
        return true;
    }

    @Override
    public void clearCommands() {
        map.clear();
    }

    @Override
    public Command getCommand(String name) {
        return map.get(name);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) throws IllegalArgumentException {
        String name = input.split(" ")[0];
        Command command = getCommand(name);
        if (command == null) return new ArrayList<>();
        return command.tabComplete(sender, input, new String[0]);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String cmdLine, Location location) throws IllegalArgumentException {
        return tabComplete(sender, cmdLine);
    }
}
