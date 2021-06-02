package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerArgument implements Argument<Player> {
    private final String name;
    private final Server server;

    public PlayerArgument(String name, Server server) {
        this.name = name;
        this.server = server;
    }

    public PlayerArgument(String name) {
        this(name, Bukkit.getServer());
    }

    @Override
    public Player parse(StringReader reader) throws CommandSyntaxException {
        Player target = server.getPlayer(reader.readUnquotedString());
        if (target == null) throw CommandSyntaxException.BuiltIn.INVALID_PLAYER.build(reader);
        return target;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        List<String> list = new ArrayList<>();
        String prefix = reader.readArgument();
        for (Player player : server.getOnlinePlayers()) {
            String name = player.getName();
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
