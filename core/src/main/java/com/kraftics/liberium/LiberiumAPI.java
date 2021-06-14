package com.kraftics.liberium;

import com.kraftics.liberium.command.CommandDispatcher;
import com.kraftics.liberium.packet.PacketProcessor;
import org.jetbrains.annotations.NotNull;

public interface LiberiumAPI {
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
