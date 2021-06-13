package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public class ShortArgument extends Argument<Short> {
    private final short min;
    private final short max;

    public ShortArgument(String name) {
        this(name, Short.MIN_VALUE);
    }

    public ShortArgument(String name, short min) {
        this(name, min, Short.MAX_VALUE);
    }

    public ShortArgument(String name, short min, short max) {
        super(name);
        this.max = max;
        this.min = min;
    }

    @Override
    public Short parse(StringReader reader) throws CommandSyntaxException {
        short value = reader.readShort();
        if (value > max) {
            throw CommandSyntaxException.BuiltIn.MORE_THAN.build("Short", max);
        } else if (value < min) {
            throw CommandSyntaxException.BuiltIn.LESS_THAN.build("Short", min);
        }
        return value;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
