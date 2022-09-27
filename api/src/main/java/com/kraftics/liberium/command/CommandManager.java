package com.kraftics.liberium.command;

import com.kraftics.liberium.LiberiumPlugin;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CommandManager {
    private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();
    private final CommandRegistrator registrator;

    public CommandManager(LiberiumPlugin plugin) {
        this.registrator = new DeferredCommandRegistrator(plugin.getNetMinecraftServer(), plugin, this);
    }

    @NotNull
    public static CommandArgumentBuilder command(String name) {
        return CommandArgumentBuilder.command(name);
    }

    @NotNull
    public static <T> RequiredArgumentBuilder<CommandSender, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public int executeOrThrow(String command, CommandSender source) throws CommandSyntaxException {
        return dispatcher.execute(command, source);
    }

    @NotNull
    public Optional<Integer> execute(String command, CommandSender sender) {
        try {
            return Optional.of(executeOrThrow(command, sender));
        } catch (CommandSyntaxException e) {
            // This is transformed to Bukkit API from vanilla Minecraft code
            StringBuilder messageBuilder = new StringBuilder(ChatColor.RED + e.getRawMessage().getString());
            if (e.getInput() != null && e.getCursor() >= 0) {
                messageBuilder.append("\n").append(ChatColor.GRAY);
                int cursor = Math.min(e.getInput().length(), e.getCursor());
                if (cursor < 10) {
                    messageBuilder.append("...");
                }
                messageBuilder.append(e.getInput(), Math.max(0, cursor - 10), cursor);
                if (cursor < e.getInput().length()) {
                    messageBuilder.append(ChatColor.RED).append(ChatColor.UNDERLINE).append(e.getInput().substring(cursor));
                }
                messageBuilder.append(ChatColor.RED).append(ChatColor.ITALIC).append("<--[HERE]");
            }
            sender.sendMessage(messageBuilder.toString());
            return Optional.empty();
        }
    }

    public void register(CommandArgumentBuilder command) {
        LiberiumCommandNode node = command.build();
        this.dispatcher.getRoot().addChild(node);
        registrator.register(node);
    }
}
