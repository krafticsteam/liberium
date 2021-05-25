package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

public class StringArgument implements Argument<String> {
    private final String name;

    public StringArgument(String name) {
        this.name = name;
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public String getName() {
        return name;
    }
}
