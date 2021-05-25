package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

public interface Argument<T> {

    T parse(StringReader reader) throws CommandSyntaxException;

    String getName();
}
