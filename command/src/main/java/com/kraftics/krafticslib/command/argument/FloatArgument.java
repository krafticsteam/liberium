package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class FloatArgument implements Argument<Float> {
    private final String name;

    public FloatArgument(String name) {
        this.name = name;
    }

    @Override
    public Float parse(StringReader reader) throws CommandSyntaxException {
        return reader.readFloat();
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
