package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BooleanArgument extends Argument<Boolean> {

    public BooleanArgument(String name) {
        super(name);
    }

    @Override
    public Boolean parse(StringReader reader) throws CommandSyntaxException {
        return reader.readBoolean();
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        if (reader.getArgument().isEmpty()) {
            return Arrays.asList("true", "false");
        } else if ("true".startsWith(reader.getArgument())) {
            return Collections.singletonList("true");
        } else if ("false".startsWith(reader.getArgument())) {
            return Collections.singletonList("false");
        }
        return null;
    }
}
