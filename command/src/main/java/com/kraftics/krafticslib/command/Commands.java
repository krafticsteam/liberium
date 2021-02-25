package com.kraftics.krafticslib.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import org.bukkit.command.CommandSender;

public class Commands {
    private Commands() {
    }

    /**
     * The command exceptions
     */
    public static final Dynamic2CommandExceptionType NOT_FOUND = new Dynamic2CommandExceptionType((name, input) -> new LiteralMessage(name + " " + input + " was not found"));
    public static final SimpleCommandExceptionType NO_PERMISSIONS_EXCEPTION = new SimpleCommandExceptionType(new LiteralMessage("You don't have enough permissions"));

    /**
     * The command result types
     */
    public static final int SUCCESS = 0;
    public static final int NO_PERMISSIONS = 1;

    /**
     * Creates literal argument builder
     *
     * @param name the literal name
     * @return created argument builder
     */
    public static LiteralArgumentBuilder<CommandSender> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Creates required argument builder
     *
     * @param name the name of the argument
     * @param type the type of the argument
     * @param <T> the type of the argument type
     * @return created argument builder
     */
    public static <T> RequiredArgumentBuilder<CommandSender, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
