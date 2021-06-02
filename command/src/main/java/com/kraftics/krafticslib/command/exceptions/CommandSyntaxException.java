package com.kraftics.krafticslib.command.exceptions;

import com.kraftics.krafticslib.command.StringReader;

public class CommandSyntaxException extends Exception {
    public static final int MAX_CONTEXT_LENGTH = 10;

    private final String message;
    private final String input;
    private final int cursor;

    public CommandSyntaxException(String message) {
        this(message, null, -1);
    }

    public CommandSyntaxException(String message, StringReader reader) {
        this(message, reader.getString(), reader.getCursor());
    }

    public CommandSyntaxException(String message, String input, int cursor) {
        super(message);

        this.message = message;
        this.input = input;
        this.cursor = cursor;
    }

    @Override
    public String getMessage() {
        String context = getContext();
        return context == null ? message : message + " at position " + cursor + ": " + context;
    }

    public String getContext() {
        if (input == null || cursor < 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        if (cursor > MAX_CONTEXT_LENGTH) {
            builder.append("...");
        }

        builder.append(input, Math.max(0, cursor - MAX_CONTEXT_LENGTH), cursor);
        builder.append("<--[HERE]");

        return builder.toString();
    }

    public String getRawMessage() {
        return message;
    }

    public String getInput() {
        return input;
    }

    public int getCursor() {
        return cursor;
    }

    public interface BuiltIn {
        DynamicCommandExceptionBuilder UNEXPECTED = new DynamicCommandExceptionBuilder((e) -> "Unexpected error happened: " + e);

        SimpleCommandExceptionBuilder INVALID_BLOCK = new SimpleCommandExceptionBuilder("Invalid block");
        SimpleCommandExceptionBuilder INVALID_ITEM = new SimpleCommandExceptionBuilder("Invalid item");
        SimpleCommandExceptionBuilder INVALID_WORLD = new SimpleCommandExceptionBuilder("Invalid world");
        SimpleCommandExceptionBuilder INVALID_PLAYER = new SimpleCommandExceptionBuilder("Invalid player");
        SimpleCommandExceptionBuilder INVALID_COMMAND = new SimpleCommandExceptionBuilder("Could not find this command");
        SimpleCommandExceptionBuilder INVALID_ARGUMENT = new SimpleCommandExceptionBuilder("This argument is not valid");
        DynamicCommandExceptionBuilder INVALID_ESCAPE = new DynamicCommandExceptionBuilder(c -> "Invalid escape '" + c + "'");

        Dynamic2CommandExceptionBuilder EXPECTED = new Dynamic2CommandExceptionBuilder((expected, found) -> "Expected " + expected + " but found " + found);
        SimpleCommandExceptionBuilder EXPECTED_ARGUMENT = new SimpleCommandExceptionBuilder("Expected an argument");
        SimpleCommandExceptionBuilder EXPECTED_ARGUMENT_SEPERATOR = new SimpleCommandExceptionBuilder("Expected an argument seperator");
    }
}
