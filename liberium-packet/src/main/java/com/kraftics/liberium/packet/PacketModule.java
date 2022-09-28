package com.kraftics.liberium.packet;

import com.kraftics.liberium.module.Module;
import com.kraftics.liberium.packet.bukkit.BukkitPacketProcessor;

public class PacketModule extends Module {
    private PacketProcessor packetProcessor;

    @Override
    public void onInit() {
        packetProcessor = new BukkitPacketProcessor(getPlugin());
    }

    @Override
    public void onEnable() {
        registerFieldAnnotation(PacketProcessorInstance.class, (o) -> packetProcessor);
    }

    @Override
    public void onDisable() {
        unregisterAnnotation(PacketProcessorInstance.class);
    }
}
