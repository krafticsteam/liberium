package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.StringReader;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

import java.util.List;

public class FloatArgument extends Argument<Float> {
    private final float min;
    private final float max;

    public FloatArgument(String name) {
        this(name, Float.MIN_VALUE);
    }

    public FloatArgument(String name, float min) {
        this(name, min, Float.MAX_VALUE);
    }

    public FloatArgument(String name, float min, float max) {
        super(name);
        this.max = max;
        this.min = min;
    }

    @Override
    public Float parse(StringReader reader) throws CommandSyntaxException {
        float value = reader.readFloat();
        if (value > max) {
            throw CommandSyntaxException.BuiltIn.MORE_THAN.build("Float", max);
        } else if (value < min) {
            throw CommandSyntaxException.BuiltIn.LESS_THAN.build("Float", min);
        }
        return value;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
