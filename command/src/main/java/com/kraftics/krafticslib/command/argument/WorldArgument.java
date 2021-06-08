package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldArgument implements Argument<World> {
    private final String name;
    private final Server server;

    public WorldArgument(String name, Server server) {
        this.name = name;
        this.server = server;
    }

    public WorldArgument(String name) {
        this(name, Bukkit.getServer());
    }

    @Override
    public World parse(StringReader reader) throws CommandSyntaxException {
        World world = server.getWorld(reader.readUnquotedString());
        if (world == null) throw CommandSyntaxException.BuiltIn.INVALID_WORLD.build(reader);
        return world;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        List<String> list = new ArrayList<>();
        String prefix = reader.getArgument();
        for (World world : server.getWorlds()) {
            String name = world.getName();
            if (name.startsWith(prefix)) {
                list.add(name);
            }
        }
        return list;
    }

    @Override
    public String getName() {
        return name;
    }
}
