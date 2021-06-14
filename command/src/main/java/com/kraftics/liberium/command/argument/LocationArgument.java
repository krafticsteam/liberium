package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.StringReader;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;
import org.bukkit.Location;

import java.util.List;

public class LocationArgument extends Argument<Location> {

    public LocationArgument(String name) {
        super(name);
    }

    @Override
    public Location parse(StringReader reader) throws CommandSyntaxException {
        double x = reader.readDouble();
        reader.skipWhitespaces();
        double y = reader.readDouble();
        reader.skipWhitespaces();
        double z = reader.readDouble();
        return new Location(null, x, y, z);
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return null;
    }
}
