package com.kraftics.krafticslib;

import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;
import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.command.CommandSender;

public interface KrafticsLibAPI {

    CommandDispatcher<CommandSender> getCommandDispatcher();
    Reflection getReflection();
    PacketProcessor getPacketProcessor();
}
