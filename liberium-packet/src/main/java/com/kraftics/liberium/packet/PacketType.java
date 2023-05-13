package com.kraftics.liberium.packet;

public interface PacketType {

    interface Play extends PacketType {

        enum Out implements Play {
            //<editor-fold desc="PacketOut" defaultstate="collapsed">
            ABILITIES,
            ANIMATION,
            BLOCK_CHANGE,
            BOSS,
            SPAWN_EXPERIENCE,
            ADVANCEMENTS,
            ATTACH_ENTITY,
            AUTO_RECIPE,
            BLOCK_ACTION,
            BLOCK_BREAK,
            BLOCK_BREAK_ANIMATION,
            CAMERA,
            CLOSE_WINDOW,
            COLLECT,
            COMBAT_EVENT,
            COMMANDS,
            CUSTOM_PAYLOAD,
            CUSTOM_SOUND_EFFECT,
            ENTITY,
            ENTITY_DESTROY,
            ENTITY_EFFECT,
            ENTITY_EQUIPMENT,
            ENTITY_HEAD_ROTATION,
            ENTITY_METADATA,
            ENTITY_SOUND,
            ENTITY_STATUS,
            ENTITY_TELEPORT,
            ENTITY_VELOCITY,
            EXPERIENCE,
            EXPLOSION,
            GAME_STATE_CHANGE,
            HELD_ITEM_SLOT,
            CHAT,
            KEEP_ALIVE,
            KICK_DISCONNECT,
            LIGHT_UPDATE,
            LOGIN,
            LOOK_AT,
            MAP,
            MAP_CHUNK,
            MOUNT,
            MULTI_BLOCK_CHANGE,
            NAMED_ENTITY_SPAWN,
            NAMED_SOUND_EFFECT,
            NBT_QUERY,
            OPEN_BOOK,
            OPEN_SIGN_EDITOR,
            OPEN_WINDOW,
            OPEN_WINDOW_HORSE,
            OPEN_WINDOW_MERCHANT,
            PLAYER_INFO,
            PLAYER_LIST_HEADER_FOOTER,
            POSITION,
            RECIPES,
            RECIPE_UPDATE,
            REMOVE_ENTITY_EFFECT,
            RESOURCE_PACK_SEND,
            RESPAWN,
            SCOREBOARD_DISPLAY_OBJECTIVE,
            SCOREBOARD_OBJECTIVE,
            SCOREBOARD_SCORE,
            SCOREBOARD_TEAM,
            SELECT_ADVANCEMENT_TAB,
            SERVER_DIFFICULTY,
            SET_COOLDOWN,
            SET_SLOT,
            SPAWN_ENTITY,
            SPAWN_ENTITY_PAINTING,
            SPAWN_POSITION,
            STATISTIC,
            STOP_SOUND,
            TAB_COMPLETE,
            TAGS,
            TILE_ENTITY_DATA,
            TITLE,
            TRANSACTION,
            UNLOAD_CHUNK,
            UPDATE_ATTRIBUTES,
            UPDATE_TIME,
            UPDATE_HEALTH,
            VEHICLE_MOVE,
            VIEW_CENTRE,
            VIEW_DISTANCE,
            WINDOW_DATA,
            WINDOW_ITEMS,
            WORLD_BORDER,
            WORLD_EVENT,
            WORLD_PARTICLES,
            SPAWN_LIVING
            //</editor-fold>
        }

        enum In implements Play {
            //<editor-fold desc="PacketIn" defaultstate="collapsed">
            ADVANCEMENTS,
            ARM_ANIMATION,
            AUTO_RECIPE,
            BEACON,
            BEDIT,
            BLOCK_DIG,
            BLOCK_PLACE,
            BOAT_MOVE,
            CLIENT_COMMAND,
            CLOSE_WINDOW,
            CUSTOM_PAYLOAD,
            DIFFICULTY_CHANGE,
            DIFFICULTY_LOCK,
            ENCHANT_ITEM,
            ENTITY_ACTION,
            ENTITY_NBT_QUERY,
            FLYING,
            HELD_ITEM_SLOT,
            CHAT,
            ITEM_NAME,
            JIGSAW_GENERATE,
            KEEP_ALIVE,
            PICK_ITEM,
            RECIPE_DISPLAYED,
            RECIPE_SETTINGS,
            RESOURCE_PACK_STATUS,
            SET_COMMAND_BLOCK,
            SET_COMMAND_MINECART,
            SET_CREATIVE_SLOT,
            SET_JIGSAW,
            SETTINGS,
            SPECTATE,
            STEER_VEHICLE,
            STRUCT,
            TAB_COMPLETE,
            TELEPORT_ACCEPT,
            TILE_NBT_QUERY,
            TRANSACTION,
            TR_SEL,
            UPDATE_SIGN,
            USE_ENTITY,
            USE_ITEM,
            VEHICLE_MOVE,
            WINDOW_CLICK,
            ABILITIES
            //</editor-fold>
        }
    }
}