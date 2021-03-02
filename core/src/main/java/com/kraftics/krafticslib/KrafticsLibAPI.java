package com.kraftics.krafticslib;

import com.kraftics.krafticslib.command.CommandDispatcher;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;

public interface KrafticsLibAPI {

    Reflection getReflection();
    PacketProcessor getPacketProcessor();
    CommandDispatcher getCommandDispatcher();
}
