package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class DoubleArgument implements Argument<Double> {
    private final String name;

    public DoubleArgument(String name) {
        this.name = name;
    }

    @Override
    public Double parse(StringReader reader) throws CommandSyntaxException {
        return reader.readDouble();
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
