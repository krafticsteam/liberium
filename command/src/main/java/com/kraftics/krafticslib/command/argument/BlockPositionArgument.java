package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.bukkit.Location;

import java.util.List;

public class BlockPositionArgument extends Argument<Location> {
    public BlockPositionArgument(String name) {
        super(name);
    }

    @Override
    public Location parse(StringReader reader) throws CommandSyntaxException {
        long x = reader.readLong();
        reader.skipWhitespaces();
        long y = reader.readLong();
        reader.skipWhitespaces();
        long z = reader.readLong();
        return new Location(null, x, y, z);
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
