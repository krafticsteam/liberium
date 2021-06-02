package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class ShortArgument implements Argument<Short> {
    private final String name;

    public ShortArgument(String name) {
        this.name = name;
    }

    @Override
    public Short parse(StringReader reader) throws CommandSyntaxException {
        return reader.readShort();
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
