package com.kraftics.liberium.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Predicate;

public class LiberiumCommandNode extends LiteralCommandNode<CommandSender> {
    private final String description;
    private final List<String> aliases;

    public LiberiumCommandNode(String literal, String description, List<String> aliases, Command<CommandSender> command, Predicate<CommandSender> requirement, CommandNode<CommandSender> redirect, RedirectModifier<CommandSender> modifier, boolean forks) {
        super(literal, command, requirement, redirect, modifier, forks);

        this.description = description;
        this.aliases = aliases;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
