package com.kraftics.liberium.nms.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.commands.CommandListenerWrapper;
import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public class CommandNodeConverter {

    // TODO: Convert modifiers
    public static LiteralArgumentBuilder<CommandListenerWrapper> convert(LiteralArgumentBuilder<CommandSender> builder) {
        if (builder == null) return null;
        LiteralArgumentBuilder<CommandListenerWrapper> result = LiteralArgumentBuilder.literal(builder.getLiteral());
        result.executes(convertExecutor(builder.getCommand()));
        result.requires(convertRequirement(builder.getRequirement()));
        result.forward(convert(builder.getRedirect()), null, builder.isFork());
        for (CommandNode<CommandSender> child : builder.getArguments()) {
            result.then(convert(child));
        }
        return result;
    }

    // TODO: Convert modifiers
    public static CommandNode<CommandListenerWrapper> convert(CommandNode<CommandSender> node) {
        if (node == null) {
            return null;
        } else if (node instanceof LiteralCommandNode) {
            return new LiteralCommandNode<>(((LiteralCommandNode<CommandSender>) node).getLiteral(), convertExecutor(node.getCommand()), convertRequirement(node.getRequirement()),
                    convert(node.getRedirect()), null, node.isFork());
        } else if (node instanceof ArgumentCommandNode) {
            ArgumentCommandNode<CommandSender, ?> argument = (ArgumentCommandNode<CommandSender, ?>) node;
            return new ArgumentCommandNode<>(node.getName(), argument.getType(), convertExecutor(node.getCommand()), convertRequirement(node.getRequirement()),
                    convert(node.getRedirect()), null, node.isFork(), convertSuggestions(argument.getCustomSuggestions(), node.getCommand()));
        } else if (node instanceof RootCommandNode) {
            RootCommandNode<CommandListenerWrapper> result = new RootCommandNode<>();
            for (CommandNode<CommandSender> child : node.getChildren()) {
                result.addChild(convert(child));
            }
            return result;
        }
        // TODO: Add warning or throw an exception
        return null;
    }

    public static Command<CommandListenerWrapper> convertExecutor(Command<CommandSender> executor) {
        if (executor == null) return null;
        return ctx -> executor.run(WrappedCommandContext.from(ctx, executor));
    }

    public static Predicate<CommandListenerWrapper> convertRequirement(Predicate<CommandSender> requirement) {
        if (requirement == null) return null;
        return wrapper -> requirement.test(wrapper.getBukkitSender());
    }

    public static SuggestionProvider<CommandListenerWrapper> convertSuggestions(SuggestionProvider<CommandSender> suggestions, Command<CommandSender> reference) {
        if (suggestions == null) return null;
        return (ctx, builder) -> suggestions.getSuggestions(WrappedCommandContext.from(ctx, reference), builder);
    }
}
