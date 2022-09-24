package com.kraftics.liberium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class LiberiumCommand extends Command {
    private final CommandDispatcher<CommandSender> dispatcher;

    protected LiberiumCommand(@NotNull LiberiumCommandNode node, @NotNull CommandDispatcher<CommandSender> dispatcher) {
        super(node.getName(), node.getDescription(), node.getUsageText(), node.getAliases()); // TODO: Better usage text
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        try {
            this.dispatcher.execute(toFull(args), sender);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            sender.sendMessage("An error happened!");
        }
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
