package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class LongArgument implements Argument<Long> {
    private final String name;

    public LongArgument(String name) {
        this.name = name;
    }

    @Override
    public Long parse(StringReader reader) throws CommandSyntaxException {
        return reader.readLong();
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
