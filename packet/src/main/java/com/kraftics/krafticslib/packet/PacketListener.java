package com.kraftics.krafticslib.packet;

public interface PacketListener {

    default void onPacketSending(PacketEvent event) {

    }

    default void onPacketReceiving(PacketEvent event) {

    }
}