package com.kraftics.liberium.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.CommandNode;
import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public interface CommandNodeConverter {

    CommandNode<Object> convert(CommandNode<CommandSender> node);
    Command<Object> convertExecutor(Command<CommandSender> executor);
    Predicate<Object> convertRequirement(Predicate<CommandSender> requirement);
}
