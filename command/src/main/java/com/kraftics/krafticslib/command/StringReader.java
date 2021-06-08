package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.List;

public class StringReader {
    public static final List<String> VALID_BOOLEANS = Arrays.asList("true", "t", "yes", "y", "false", "f", "no", "n");

    private int cursor;
    private final String string;

    public StringReader(String string, int cursor) {
        this.string = string;
        this.cursor = cursor;
    }

    public StringReader(StringReader reader) {
        this(reader.string, reader.cursor);
    }

    public StringReader(String string) {
        this(string, 0);
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public String getString() {
        return string;
    }

    public String getRemaining() {
        return string.substring(cursor);
    }

    public String getRange(int start) {
        return getRange(start, cursor);
    }

    public String getRange(int start, int end) {
        return string.substring(start, end);
    }

    public boolean canRead(int length) {
        return cursor + length <= string.length();
    }

    public boolean canRead() {
        return canRead(1);
    }

    public char peek() {
        return string.charAt(cursor);
    }

    public char read() {
        return string.charAt(cursor++);
    }

    public void skip() {
        cursor++;
    }

    public void skipWhitespaces() {
        while (canRead() && Character.isWhitespace(peek())) {
            skip();
        }
    }

    public String readString() throws CommandSyntaxException {
        if (!canRead()) {
            return "";
        }

        char peek = peek();
        if (peek == '"') {
            return readQuotedString();
        }
        return readUnquotedString();
    }

    public String readUnquotedString() {
        int start = cursor;
        while (canRead() && peek() != ' ') {
            skip();
        }
        return string.substring(start, cursor);
    }

    public String readQuotedString() throws CommandSyntaxException {
        if (!canRead()) return "";

        char read = read();
        if (read != '\'' && read != '"') {
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("start of a quoted string", read, this);
        }

        StringBuilder builder = new StringBuilder();
        boolean escaped = false;

        while (canRead()) {
            char c = read();

            if (escaped) {
                if (c == '"' || c == '\\') {
                    builder.append(c);
                    escaped = false;
                } else {
                    throw CommandSyntaxException.BuiltIn.INVALID_ESCAPE.build(c, this);
                }
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                return builder.toString();
            } else {
                builder.append(c);
            }
        }

        throw new CommandSyntaxException("Expected end of a quoted string", this);
    }

    public int readInteger() throws CommandSyntaxException {
        int start = cursor;
        while (canRead() && isNumber(peek())) {
            skip();
        }
        String number = string.substring(start, cursor);
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            cursor = start;
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("an integer", number, this);
        }
    }

    public long readLong() throws CommandSyntaxException {
        int start = cursor;
        while (canRead() && isNumber(peek())) {
            skip();
        }
        String number = string.substring(start, cursor);
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            cursor = start;
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("a long", number, this);
        }
    }

    public short readShort() throws CommandSyntaxException {
        int start = cursor;
        while (canRead() && isNumber(peek())) {
            skip();
        }
        String number = string.substring(start, cursor);
        try {
            return Short.parseShort(number);
        } catch (NumberFormatException e) {
            cursor = start;
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("a short", number, this);
        }
    }

    public float readFloat() throws CommandSyntaxException {
        int start = cursor;
        while (canRead() && isDecimal(peek())) {
            skip();
        }
        String number = string.substring(start, cursor);
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            cursor = start;
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("a float", number, this);
        }
    }

    public double readDouble() throws CommandSyntaxException {
        int start = cursor;
        while (canRead() && isDecimal(peek())) {
            skip();
        }
        String number = string.substring(start, cursor);
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            cursor = start;
            throw CommandSyntaxException.BuiltIn.EXPECTED.build("a double", number, this);
        }
    }

    public boolean readBoolean() throws CommandSyntaxException {
        int start = cursor;
        String bool = readUnquotedString();

        if (bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("t") ||
                bool.equalsIgnoreCase("y") || bool.equalsIgnoreCase("yes")) {
            return true;
        } else if (bool.equalsIgnoreCase("false") || bool.equalsIgnoreCase("f") ||
                bool.equalsIgnoreCase("n") || bool.equalsIgnoreCase("no")) {
            return false;
        }

        cursor = start;
        throw CommandSyntaxException.BuiltIn.EXPECTED.build("a boolean", bool, this);
    }

    public int getArgumentStart() {
        int cursor = this.cursor;
        while (reversePeek(cursor) != ' ') {
            cursor--;
        }
        return cursor;
    }

    public String getArgument() {
        int start = cursor;
        cursor = getArgumentStart();
        String argument = readUnquotedString();
        cursor = start;
        return argument;
    }

    private char reversePeek(int cursor) {
        return string.charAt(--cursor);
    }

    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9' || c == '-';
    }

    public static boolean isDecimal(char c) {
        return c >= '0' && c <= '9' || c == '.' || c == '-';
    }
}
