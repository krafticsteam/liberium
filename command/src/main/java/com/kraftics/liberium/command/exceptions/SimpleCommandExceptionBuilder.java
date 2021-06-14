package com.kraftics.liberium.command.exceptions;

import com.kraftics.liberium.command.StringReader;

public class SimpleCommandExceptionBuilder {
    private final String message;

    public SimpleCommandExceptionBuilder(String message) {
        this.message = message;
    }

    public CommandSyntaxException build() {
        return new CommandSyntaxException(message);
    }

    public CommandSyntaxException build(StringReader reader) {
        return new CommandSyntaxException(message, reader);
    }

}