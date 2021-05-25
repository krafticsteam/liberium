package com.kraftics.krafticslib.command.test;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeCommandMap implements CommandMap {
    private final List<Command> commands = new ArrayList<>();

    @Override
    public void registerAll(@NotNull String fallbackPrefix, @NotNull List<Command> commands) {
        commands.forEach(command -> register(fallbackPrefix, command));
    }

    @Override
    public boolean register(@NotNull String label, @NotNull String fallbackPrefix, @NotNull Command command) {
        commands.add(command);
        return true;
    }

    @Override
    public boolean register(@NotNull String fallbackPrefix, @NotNull Command command) {
        commands.add(command);
        return true;
    }

    @Override
    public boolean dispatch(@NotNull CommandSender sender, @NotNull String cmdLine) throws CommandException {
        String[] split = cmdLine.split(" ");
        String commandName = split[0];
        Command command = getCommand(commandName);
        if (command == null) throw new CommandException("Invalid command");
        return command.execute(sender, commandName, Arrays.copyOfRange(split, 1, split.length));
    }

    @Override
    public void clearCommands() {
        commands.clear();
    }

    @Nullable
    @Override
    public Command getCommand(@NotNull String name) {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine) throws IllegalArgumentException {
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine, @Nullable Location location) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}
