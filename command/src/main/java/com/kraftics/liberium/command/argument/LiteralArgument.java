package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.StringReader;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

import java.util.Collections;
import java.util.List;

public class LiteralArgument extends Argument<Void> {
    public LiteralArgument(String name) {
        super(name);
    }

    @Override
    public Void parse(StringReader reader) throws CommandSyntaxException {
        String literal = reader.readUnquotedString();
        if (getName().equals(literal)) {
            return null;
        }
        throw CommandSyntaxException.BuiltIn.EXPECTED_LITERAL.build(getName());
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return Collections.singletonList(getName());
    }
}
