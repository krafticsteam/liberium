package com.kraftics.liberium.nms;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

public interface NetMinecraftServer {

    CommandMap getCommandMap();

    CommandDispatcher<Object> getServerDispatcher();

    void registerConvertedCommand(CommandNode<CommandSender> node);

    void registerConvertedCommand(LiteralArgumentBuilder<CommandSender> builder);
}
