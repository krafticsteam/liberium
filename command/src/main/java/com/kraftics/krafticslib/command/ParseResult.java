package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.Map;

public class ParseResult {
    private final Map<CommandNode, CommandSyntaxException> exceptions;
    private final CommandContextBuilder context;
    private final StringReader reader;

    public ParseResult(StringReader reader, CommandContextBuilder context, Map<CommandNode, CommandSyntaxException> exceptions) {
        this.exceptions = exceptions;
        this.context = context;
        this.reader = reader;
    }

    public Map<CommandNode, CommandSyntaxException> getExceptions() {
        return exceptions;
    }

    public CommandContextBuilder getContext() {
        return context;
    }

    public StringReader getReader() {
        return reader;
    }
}
