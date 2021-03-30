package com.kraftics.krafticslib.packet;

/**
 * This class is used to listen to packets (r/unexpected)
 */
public interface PacketListener {

    default void onPacketSending(PacketEvent event) {

    }

    default void onPacketReceiving(PacketEvent event) {

    }
}