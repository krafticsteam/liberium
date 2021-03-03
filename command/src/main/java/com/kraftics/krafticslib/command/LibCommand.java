package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

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
        command.execute(CommandContext.create(sender, label));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> list = command.tabComplete(TabContext.create(sender, alias));
        if (list == null) return Collections.emptyList();
        return list;
    }
}
