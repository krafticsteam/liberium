package com.kraftics.liberium.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.SingleRedirectModifier;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CommandArgumentBuilder extends LiteralArgumentBuilder<CommandSender> {
    private String description = "";
    private final List<String> aliases = new ArrayList<>();

    protected CommandArgumentBuilder(String name) {
        super(name);
    }

    public static CommandArgumentBuilder command(@NotNull String name) {
        Validate.notNull(name, "Name cannot be null");
        return new CommandArgumentBuilder(name);
    }

    public CommandArgumentBuilder description(@NotNull String description) {
        Validate.notNull(description, "Description cannot be null");
        this.description = description;
        return this;
    }

    public CommandArgumentBuilder alias(@NotNull String alias) {
        Validate.notNull(alias, "Alias cannot be null");
        this.aliases.add(alias);
        return this;
    }

    @Override
    public CommandArgumentBuilder then(CommandNode<CommandSender> argument) {
        super.then(argument);
        return this;
    }

    @Override
    public CommandArgumentBuilder then(ArgumentBuilder<CommandSender, ?> argument) {
        super.then(argument);
        return this;
    }

    @Override
    public CommandArgumentBuilder executes(Command<CommandSender> command) {
        super.executes(command);
        return this;
    }

    @Override
    public CommandArgumentBuilder requires(Predicate<CommandSender> requirement) {
        super.requires(requirement);
        return this;
    }

    @Override
    public CommandArgumentBuilder redirect(CommandNode<CommandSender> target) {
        super.redirect(target);
        return this;
    }

    @Override
    public CommandArgumentBuilder redirect(CommandNode<CommandSender> target, SingleRedirectModifier<CommandSender> modifier) {
        super.redirect(target, modifier);
        return this;
    }

    @Override
    public CommandArgumentBuilder fork(CommandNode<CommandSender> target, RedirectModifier<CommandSender> modifier) {
        super.fork(target, modifier);
        return this;
    }

    @Override
    public CommandArgumentBuilder forward(CommandNode<CommandSender> target, RedirectModifier<CommandSender> modifier, boolean fork) {
        super.forward(target, modifier, fork);
        return this;
    }

    @Override
    protected CommandArgumentBuilder getThis() {
        return this;
    }

    @Override
    public LiberiumCommandNode build() {
        final LiberiumCommandNode result = new LiberiumCommandNode(getLiteral(), description, aliases, getCommand(), getRequirement(), getRedirect(), getRedirectModifier(), isFork());

        for (final CommandNode<CommandSender> argument : getArguments()) {
            result.addChild(argument);
        }

        return result;
    }
}
