package com.kraftics.liberium.command.exceptions;

import com.kraftics.liberium.command.StringReader;

import java.util.function.Function;

public class DynamicCommandExceptionBuilder {
    private final Function<Object, String> function;

    public DynamicCommandExceptionBuilder(Function<Object, String> function) {
        this.function = function;
    }

    public CommandSyntaxException build(Object arg) {
        return new CommandSyntaxException(function.apply(arg));
    }

    public CommandSyntaxException build(Object arg, StringReader reader) {
        return new CommandSyntaxException(function.apply(arg), reader);
    }
}
