package com.kraftics.liberium.command;

import com.kraftics.liberium.command.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.List;

/**
 * Class used to read string step-by-step
 */
public class StringReader {
    public static final List<String> VALID_BOOLEANS = Arrays.asList("true", "t", "yes", "y", "false", "f", "no", "n");

    private int cursor;
    private final String string;

    /**
     * Creates new StringReader with string and starting cursor
     *
     * @param string the string that should be read
     * @param cursor the starting cursor
     */
    public StringReader(String string, int cursor) {
        this.string = string;
        this.cursor = cursor;
    }

    /**
     * Copies a StringReader
     *
     * @param reader the StringReader
     */
    public StringReader(StringReader reader) {
        this(reader.string, reader.cursor);
    }

    /**
     * Creates new StringReader with string and starting cursor at 0
     *
     * @param string the string
     */
    public StringReader(String string) {
        this(string, 0);
    }

    /**
     * Gets current cursor
     *
     * @return the cursor
     */
    public int getCursor() {
        return cursor;
    }

    /**
     * Sets current cursor
     *
     * @param cursor the cursor
     */
    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    /**
     * Gets current string
     *
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * Gets remaining sequence from current cursor to end in current string
     *
     * @return the sequence
     */
    public String getRemaining() {
        return string.substring(cursor);
    }

    /**
     * Gets sequence range from starting value to current cursor in current string
     *
     * @param start the starting value
     * @return the sequence
     */
    public String getRange(int start) {
        return getRange(start, cursor);
    }

    /**
     * Gets sequence range from starting value to ending value in current string
     *
     * @param start the starting value
     * @param end the ending value
     * @return the sequence
     */
    public String getRange(int start, int end) {
        return string.substring(start, end);
    }

    /**
     * Checks if this StringReader can read for an amount of characters
     *
     * @param length the amount of characters
     * @return if can read
     */
    public boolean canRead(int length) {
        return cursor + length <= string.length();
    }

    /**
     * Checks if this StringReader can read 1 character
     *
     * @return if can read
     */
    public boolean canRead() {
        return canRead(1);
    }

    /**
     * Peeks the next character
     *
     * @return the peeked character
     */
    public char peek() {
        return string.charAt(cursor);
    }

    /**
     * Reads the next character and moves the cursor by one
     *
     * @return the read character
     */
    public char read() {
        return string.charAt(cursor++);
    }

    /**
     * Skips the next character - moves the cursor by one
     */
    public void skip() {
        cursor++;
    }

    /**
     * Skips all whitespaces
     */
    public void skipWhitespaces() {
        while (canRead() && Character.isWhitespace(peek())) {
            skip();
        }
    }

    /**
     * Reads a string.
     * <p>
     * When the string is starting with a quote ("), then the string will be read
     * until the next quote. If not, then the string will be read until a whitespace
     * </p>
     *
     * @return the string
     * @throws CommandSyntaxException when the string could not be read
     */
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

    /**
     * Reads unquoted string.
     * <p>
     * Unquoted string is a string read until a whitespace.
     * In this string there cannot be any whitespace.
     * </p>
     *
     * @return the string
     */
    public String readUnquotedString() {
        int start = cursor;
        while (canRead() && peek() != ' ') {
            skip();
        }
        return string.substring(start, cursor);
    }

    /**
     * Reads quoted string.
     * <p>
     * Quoted string is a string starting with a quote (") and also ending with a quote.
     * This string <strong>can</strong> contain quotes using an escape: \"<br>
     * You can also escape the \ character: \\
     * </p>
     *
     * @return the string
     * @throws CommandSyntaxException when the string is not a valid quote string
     */
    public String readQuotedString() throws CommandSyntaxException {
        if (!canRead()) return "";

        char read = read();
        if (read != '\'' && read != '"') {
            cursor--;
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

    /**
     * Reads an integer
     *
     * @return the integer
     * @throws CommandSyntaxException when the integer is invalid
     */
    public int readInteger() throws CommandSyntaxException {
        int start = cursor;
        while (canRead()) {
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

    /**
     * Reads a long
     *
     * @return the long
     * @throws CommandSyntaxException when the long is invalid
     */
    public long readLong() throws CommandSyntaxException {
        int start = cursor;
        while (canRead()) {
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

    /**
     * Reads a short
     *
     * @return the short
     * @throws CommandSyntaxException when the short is invalid
     */
    public short readShort() throws CommandSyntaxException {
        int start = cursor;
        while (canRead()) {
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

    /**
     * Reads a float
     *
     * @return the float
     * @throws CommandSyntaxException when the float is invalid
     */
    public float readFloat() throws CommandSyntaxException {
        int start = cursor;
        while (canRead()) {
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

    /**
     * Reads a double
     *
     * @return the double
     * @throws CommandSyntaxException when the double is invalid
     */
    public double readDouble() throws CommandSyntaxException {
        int start = cursor;
        while (canRead()) {
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

    /**
     * Reads a boolean
     *
     * @return the boolean
     * @throws CommandSyntaxException when the boolean is invalid
     */
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

    /**
     * Gets an index of argument in current cursor starting.
     * Used in {@link #getArgument()}
     *
     * @return the index
     */
    public int getArgumentStart() {
        int cursor = this.cursor;
        while (reversePeek(cursor) != ' ') {
            cursor--;
        }
        return cursor;
    }

    /**
     * Reads an argument from start to end
     * <p>
     * This is achieved by reading backwards until a whitespace and then reading an unquoted string
     * </p>
     *
     * @return the argument
     */
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
}
