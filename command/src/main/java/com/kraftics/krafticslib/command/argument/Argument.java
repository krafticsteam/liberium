package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.ArrayList;
import java.util.List;

public abstract class Argument<T> {
    private final String name;

    public Argument(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public abstract T parse(StringReader reader) throws CommandSyntaxException;

    public abstract List<String> tabComplete(StringReader reader) throws CommandSyntaxException;

    public static List<String> contextOnly(List<?> original, StringReader reader) {
        List<String> result = new ArrayList<>();
        String prefix = reader.getArgument();
        for (Object o : original) {
            String s = o.toString();
            if (s.startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }
}
