package com.kraftics.liberium.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class LiberiumCommand extends Command {
    private final CommandManager commandManager;

    protected LiberiumCommand(@NotNull LiberiumCommandNode node, @NotNull CommandManager commandManager) {
        super(node.getName(), node.getDescription(), node.getUsageText(), node.getAliases()); // TODO: Better usage text
        this.commandManager = commandManager;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.commandManager.execute(toFull(args), sender);
        return true;
    }

    protected String toFull(String[] args) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(getName());
        for (String arg : args) {
            joiner.add(arg);
        }
        return joiner.toString();
    }
}
