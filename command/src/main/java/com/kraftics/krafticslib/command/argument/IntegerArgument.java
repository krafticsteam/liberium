package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class IntegerArgument extends Argument<Integer> {
    private final int min;
    private final int max;

    public IntegerArgument(String name) {
        this(name, Integer.MIN_VALUE);
    }

    public IntegerArgument(String name, int min) {
        this(name, min, Integer.MAX_VALUE);
    }

    public IntegerArgument(String name, int min, int max) {
        super(name);
        this.max = max;
        this.min = min;
    }

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        int value = reader.readInteger();
        if (value > max) {
            throw CommandSyntaxException.BuiltIn.MORE_THAN.build("Integer", max);
        } else if (value < min) {
            throw CommandSyntaxException.BuiltIn.LESS_THAN.build("Integer", min);
        }
        return value;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
