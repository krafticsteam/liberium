package com.kraftics.liberium.nms;

import com.kraftics.liberium.command.CommandNodeConverter;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandMap;

public interface NetMinecraftServer {

    CommandMap getCommandMap();

    CommandDispatcher<Object> getServerDispatcher();

    CommandNodeConverter getCommandNodeConverter();
}
