package com.kraftics.krafticslib;

import com.kraftics.krafticslib.command.CommandDispatcher;
import com.kraftics.krafticslib.packet.PacketProcessor;
import com.kraftics.krafticslib.packet.Reflection;
import org.jetbrains.annotations.NotNull;

public interface KrafticsLibAPI {

    /**
     * Gets reflection from this api
     *
     * @return the reflection
     */
    @NotNull
    Reflection getReflection();

    /**
     * Gets packet processor from this api
     *
     * @return the packet processor
     */
    @NotNull
    PacketProcessor getPacketProcessor();

    /**
     * Gets command dispatcher from this api
     *
     * @return the command dispatcher
     */
    @NotNull
    CommandDispatcher getCommandDispatcher();
}
