package com.kraftics.krafticslib.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "Checks the ping of a player", "pong");
    }

    @Override
    public void execute(CommandContext context) {
        if (context.getArguments().size() <= 0) {
            context.getSender().sendMessage("Please enter name of a player");
            return;
        }

        context.getSender().sendMessage("The ping of player " + context.getArgument(0) + " is unknown");
    }

    @Override
    public List<String> tabComplete(TabContext context) {
        List<String> list = new ArrayList<>();
        if (context.getIndex() == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(context.getRemaining())) {
                    list.add(player.getName());
                }
            }
            return list;
        }
        return list;
    }
}
