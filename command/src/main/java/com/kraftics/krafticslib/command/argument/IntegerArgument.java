package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class IntegerArgument implements Argument<Integer> {
    private final String name;

    public IntegerArgument(String name) {
        this.name = name;
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        return reader.readInteger();
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
