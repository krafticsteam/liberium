package com.kraftics.krafticslib;

import com.kraftics.krafticslib.listener.CommandListener;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static com.kraftics.krafticslib.command.Commands.*;
import static com.kraftics.krafticslib.command.arguments.PlayerArgumentType.getPlayer;
import static com.kraftics.krafticslib.command.arguments.PlayerArgumentType.player;

public class KrafticsLibPlugin extends JavaPlugin {
    private CommandListener commandListener;
    private KrafticsLibAPI api;

    @Override
    public void onEnable() {
        commandListener = new CommandListener();
        getServer().getPluginManager().registerEvents(commandListener, this);

        api = KrafticsLib.create(this);
        CommandDispatcher<CommandSender> dispatcher = api.getCommandDispatcher();
        PacketProcessor processor = api.getPacketProcessor();

        dispatcher.register(literal("ping")
                .then(argument("player", player()).executes(context -> {
                    Player target = getPlayer(context, "player");
                    int ping = processor.getPing(target);
                    context.getSource().sendMessage("Ping of player " + target.getName() + " is " + ping);
                    return SUCCESS;
                }).executes(context -> {
                    context.getSource().sendMessage("Please enter name of the player");
                    return SUCCESS;
                })));
    }
}
