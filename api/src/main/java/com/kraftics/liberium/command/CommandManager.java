package com.kraftics.liberium.command;

import com.kraftics.liberium.nms.NetMinecraftServer;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;

public class CommandManager {
    private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();
    private final NetMinecraftServer nms;

    public CommandManager(NetMinecraftServer nms) {
        this.nms = nms;
    }

    public void register(CommandArgumentBuilder command) {
        LiberiumCommandNode node = command.build();
        this.dispatcher.getRoot().addChild(node);
        this.nms.getCommandMap().register("temporarypref", new LiberiumCommand(node, dispatcher));
        this.nms.registerConvertedCommand(node);
    }
}
