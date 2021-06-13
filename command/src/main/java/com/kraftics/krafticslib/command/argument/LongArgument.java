package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class LongArgument extends Argument<Long> {
    private final long min;
    private final long max;

    public LongArgument(String name) {
        this(name, Long.MIN_VALUE);
    }

    public LongArgument(String name, long min) {
        this(name, min, Long.MAX_VALUE);
    }

    public LongArgument(String name, long min, long max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public Long parse(StringReader reader) throws CommandSyntaxException {
        long value = reader.readLong();
        if (value > max) {
            throw CommandSyntaxException.BuiltIn.MORE_THAN.build("Long", max);
        } else if (value < min) {
            throw CommandSyntaxException.BuiltIn.LESS_THAN.build("Long", min);
        }
        return value;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
