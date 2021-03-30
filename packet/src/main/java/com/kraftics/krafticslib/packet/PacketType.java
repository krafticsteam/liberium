package com.kraftics.krafticslib.packet;

public interface PacketType {

    interface Play extends PacketType {

        enum Out implements Play {
            ABILITIES,
            ANIMATION,
            BLOCK_CHANGE,
            BOSS,
            SPAWN_EXPERIENCE,
            SPAWN_LIVING
        }

        enum In implements Play {

        }
    }
}
