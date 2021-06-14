package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.StringReader;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

import java.util.List;

public class DoubleArgument extends Argument<Double> {
    private final double max;
    private final double min;

    public DoubleArgument(String name) {
        this(name, Double.MIN_VALUE);
    }

    public DoubleArgument(String name, double min) {
        this(name, min, Double.MAX_VALUE);
    }

    public DoubleArgument(String name, double min, double max) {
        super(name);
        this.max = max;
        this.min = min;
    }

    @Override
    public Double parse(StringReader reader) throws CommandSyntaxException {
        double value = reader.readDouble();
        if (value > max) {
            throw CommandSyntaxException.BuiltIn.MORE_THAN.build("Double", max);
        } else if (value < min) {
            throw CommandSyntaxException.BuiltIn.LESS_THAN.build("Double", min);
        }
        return value;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
