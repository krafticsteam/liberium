package com.kraftics.liberium.command.exceptions;

import com.kraftics.liberium.command.StringReader;

public class Dynamic2CommandExceptionBuilder {
    private final Function function;

    public Dynamic2CommandExceptionBuilder(Function function) {
        this.function = function;
    }

    public CommandSyntaxException build(Object a, Object b) {
        return new CommandSyntaxException(function.apply(a, b));
    }

    public CommandSyntaxException build(Object a, Object b, StringReader reader) {
        return new CommandSyntaxException(function.apply(a, b), reader);
    }

    @FunctionalInterface
    public interface Function {
        String apply(Object a, Object b);
    }
}
