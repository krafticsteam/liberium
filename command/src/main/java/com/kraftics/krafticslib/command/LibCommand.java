package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class LibCommand extends org.bukkit.command.Command {
    private final Command command;

    protected LibCommand(Command command) {
        super(command.getName(), command.getDescription(), "/" + command.getName(), command.getAliases());
        setPermission(command.getPermission());
        setPermissionMessage(command.getPermissionMessage());

        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(label);
        for (String arg : args) {
            joiner.add(arg);
        }

        command.execute(CommandContext.create(sender, joiner.toString()));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(alias);
        for (String arg : args) {
            joiner.add(arg);
        }

        List<String> list = command.tabComplete(TabContext.create(sender, joiner.toString()));
        if (list == null) return Collections.emptyList();
        return list;
    }
}
