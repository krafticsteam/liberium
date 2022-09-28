package com.kraftics.liberium.packet;

import com.kraftics.liberium.packet.reflection.ConstructorInvoker;
import com.kraftics.liberium.packet.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to register packet types
 */
public class PacketRegistry {
    private static final Map<Class<?>, PacketType> REGISTRY = new HashMap<>();
    private static final Map<PacketType, Class<?>> REVERSE_REGISTRY = new HashMap<>();

    static {
        //<editor-fold desc="PacketOut" defaultstate="collapsed">
        register("PacketPlayOutAbilities", PacketType.Play.Out.ABILITIES);
        register("PacketPlayOutAdvancements", PacketType.Play.Out.ADVANCEMENTS);
        register("PacketPlayOutAnimation", PacketType.Play.Out.ANIMATION);
        register("PacketPlayOutAttachEntity", PacketType.Play.Out.ATTACH_ENTITY);
        register("PacketPlayOutAutoRecipe", PacketType.Play.Out.AUTO_RECIPE);
        register("PacketPlayOutBlockAction", PacketType.Play.Out.BLOCK_ACTION);
        register("PacketPlayOutBlockBreak", PacketType.Play.Out.BLOCK_BREAK);
        register("PacketPlayOutBlockBreakAnimation", PacketType.Play.Out.BLOCK_BREAK_ANIMATION);
        register("PacketPlayOutBlockChange", PacketType.Play.Out.BLOCK_CHANGE);
        register("PacketPlayOutBoss", PacketType.Play.Out.BOSS);
        register("PacketPlayOutCamera", PacketType.Play.Out.CAMERA);
        register("PacketPlayOutCloseWindow", PacketType.Play.Out.CLOSE_WINDOW);
        register("PacketPlayOutCollect", PacketType.Play.Out.COLLECT);
        register("PacketPlayOutCombatEvent", PacketType.Play.Out.COMBAT_EVENT);
        register("PacketPlayOutCommands", PacketType.Play.Out.COMMANDS);
        register("PacketPlayOutCustomPayload", PacketType.Play.Out.CUSTOM_PAYLOAD);
        register("PacketPlayOutCustomSoundEffect", PacketType.Play.Out.CUSTOM_SOUND_EFFECT);
        register("PacketPlayOutEntity", PacketType.Play.Out.ENTITY);
        register("PacketPlayOutEntityDestroy", PacketType.Play.Out.ENTITY_DESTROY);
        register("PacketPlayOutEntityEffect", PacketType.Play.Out.ENTITY_EFFECT);
        register("PacketPlayOutEntityEquipment", PacketType.Play.Out.ENTITY_EQUIPMENT);
        register("PacketPlayOutEntityHeadRotation", PacketType.Play.Out.ENTITY_HEAD_ROTATION);
        register("PacketPlayOutEntityMetadata", PacketType.Play.Out.ENTITY_METADATA);
        register("PacketPlayOutEntitySound", PacketType.Play.Out.ENTITY_SOUND);
        register("PacketPlayOutEntityStatus", PacketType.Play.Out.ENTITY_STATUS);
        register("PacketPlayOutEntityTeleport", PacketType.Play.Out.ENTITY_TELEPORT);
        register("PacketPlayOutEntityVelocity", PacketType.Play.Out.ENTITY_VELOCITY);
        register("PacketPlayOutExperience", PacketType.Play.Out.EXPERIENCE);
        register("PacketPlayOutExplosion", PacketType.Play.Out.EXPLOSION);
        register("PacketPlayOutGameStateChange", PacketType.Play.Out.GAME_STATE_CHANGE);
        register("PacketPlayOutHeldItemSlot", PacketType.Play.Out.HELD_ITEM_SLOT);
        register("PacketPlayOutChat", PacketType.Play.Out.CHAT);
        register("PacketPlayOutKeepAlive", PacketType.Play.Out.KEEP_ALIVE);
        register("PacketPlayOutKickDisconnect", PacketType.Play.Out.KICK_DISCONNECT);
        register("PacketPlayOutLightUpdate", PacketType.Play.Out.LIGHT_UPDATE);
        register("PacketPlayOutLogin", PacketType.Play.Out.LOGIN);
        register("PacketPlayOutLookAt", PacketType.Play.Out.LOOK_AT);
        register("PacketPlayOutMap", PacketType.Play.Out.MAP);
        register("PacketPlayOutMapChunk", PacketType.Play.Out.MAP_CHUNK);
        register("PacketPlayOutMount", PacketType.Play.Out.MOUNT);
        register("PacketPlayOutMultiBlockChange", PacketType.Play.Out.MULTI_BLOCK_CHANGE);
        register("PacketPlayOutNamedEntitySpawn", PacketType.Play.Out.NAMED_ENTITY_SPAWN);
        register("PacketPlayOutNamedSoundEffect", PacketType.Play.Out.NAMED_SOUND_EFFECT);
        register("PacketPlayOutNBTQuery", PacketType.Play.Out.NBT_QUERY);
        register("PacketPlayOutOpenBook", PacketType.Play.Out.OPEN_BOOK);
        register("PacketPlayOutOpenSignEditor", PacketType.Play.Out.OPEN_SIGN_EDITOR);
        register("PacketPlayOutOpenWindow", PacketType.Play.Out.OPEN_WINDOW);
        register("PacketPlayOutOpenWindowHorse", PacketType.Play.Out.OPEN_WINDOW_HORSE);
        register("PacketPlayOutOpenWindowMerchant", PacketType.Play.Out.OPEN_WINDOW_MERCHANT);
        register("PacketPlayOutPlayerInfo", PacketType.Play.Out.PLAYER_INFO);
        register("PacketPlayOutPlayerListHeaderFooter", PacketType.Play.Out.PLAYER_LIST_HEADER_FOOTER);
        register("PacketPlayOutPosition", PacketType.Play.Out.POSITION);
        register("PacketPlayOutRecipes", PacketType.Play.Out.RECIPES);
        register("PacketPlayOutRecipeUpdate", PacketType.Play.Out.RECIPE_UPDATE);
        register("PacketPlayOutRemoveEntityEffect", PacketType.Play.Out.REMOVE_ENTITY_EFFECT);
        register("PacketPlayOutResourcePackSend", PacketType.Play.Out.RESOURCE_PACK_SEND);
        register("PacketPlayOutRespawn", PacketType.Play.Out.RESPAWN);
        register("PacketPlayOutScoreboardDisplayObjective", PacketType.Play.Out.SCOREBOARD_DISPLAY_OBJECTIVE);
        register("PacketPlayOutScoreboardObjective", PacketType.Play.Out.SCOREBOARD_OBJECTIVE);
        register("PacketPlayOutScoreboardScore", PacketType.Play.Out.SCOREBOARD_SCORE);
        register("PacketPlayOutScoreboardTeam", PacketType.Play.Out.SCOREBOARD_TEAM);
        register("PacketPlayOutSelectAdvancementTab", PacketType.Play.Out.SELECT_ADVANCEMENT_TAB);
        register("PacketPlayOutServerDifficulty", PacketType.Play.Out.SERVER_DIFFICULTY);
        register("PacketPlayOutSetCooldown", PacketType.Play.Out.SET_COOLDOWN);
        register("PacketPlayOutSetSlot", PacketType.Play.Out.SET_SLOT);
        register("PacketPlayOutSpawnEntity", PacketType.Play.Out.SPAWN_ENTITY);
        register("PacketPlayOutSpawnEntityExperienceOrb", PacketType.Play.Out.SPAWN_EXPERIENCE);
        register("PacketPlayOutSpawnEntityLiving", PacketType.Play.Out.SPAWN_LIVING);
        register("PacketPlayOutSpawnEntityPainting", PacketType.Play.Out.SPAWN_ENTITY_PAINTING);
        register("PacketPlayOutSpawnPosition", PacketType.Play.Out.SPAWN_POSITION);
        register("PacketPlayOutStatistic", PacketType.Play.Out.STATISTIC);
        register("PacketPlayOutStopSound", PacketType.Play.Out.STOP_SOUND);
        register("PacketPlayOutTabComplete", PacketType.Play.Out.TAB_COMPLETE);
        register("PacketPlayOutTags", PacketType.Play.Out.TAGS);
        register("PacketPlayOutTileEntityData", PacketType.Play.Out.TILE_ENTITY_DATA);
        register("PacketPlayOutTitle", PacketType.Play.Out.TITLE);
        register("PacketPlayOutTransaction", PacketType.Play.Out.TRANSACTION);
        register("PacketPlayOutUnloadChunk", PacketType.Play.Out.UNLOAD_CHUNK);
        register("PacketPlayOutUpdateAttributes", PacketType.Play.Out.UPDATE_ATTRIBUTES);
        register("PacketPlayOutUpdateHealth", PacketType.Play.Out.UPDATE_HEALTH);
        register("PacketPlayOutUpdateTime", PacketType.Play.Out.UPDATE_TIME);
        register("PacketPlayOutVehicleMove", PacketType.Play.Out.VEHICLE_MOVE);
        register("PacketPlayOutViewCentre", PacketType.Play.Out.VIEW_CENTRE);
        register("PacketPlayOutViewDistance", PacketType.Play.Out.VIEW_DISTANCE);
        register("PacketPlayOutWindowData", PacketType.Play.Out.WINDOW_DATA);
        register("PacketPlayOutWindowItems", PacketType.Play.Out.WINDOW_ITEMS);
        register("PacketPlayOutWorldBorder", PacketType.Play.Out.WORLD_BORDER);
        register("PacketPlayOutWorldEvent", PacketType.Play.Out.WORLD_EVENT);
        register("PacketPlayOutWorldParticles", PacketType.Play.Out.WORLD_PARTICLES);
        //</editor-fold>

        //<editor-fold desc="PacketIn" defaultstate="collapsed">
        register("PacketPlayInAbilities", PacketType.Play.In.ABILITIES);
        register("PacketPlayInAdvancements", PacketType.Play.In.ADVANCEMENTS);
        register("PacketPlayInArmAnimation", PacketType.Play.In.ARM_ANIMATION);
        register("PacketPlayInAutoRecipe", PacketType.Play.In.AUTO_RECIPE);
        register("PacketPlayInBeacon", PacketType.Play.In.BEACON);
        register("PacketPlayInBEdit", PacketType.Play.In.BEDIT);
        register("PacketPlayInBlockDig", PacketType.Play.In.BLOCK_DIG);
        register("PacketPlayInBlockPlace", PacketType.Play.In.BLOCK_PLACE);
        register("PacketPlayInBoatMove", PacketType.Play.In.BOAT_MOVE);
        register("PacketPlayInClientCommand", PacketType.Play.In.CLIENT_COMMAND);
        register("PacketPlayInCloseWindow", PacketType.Play.In.CLOSE_WINDOW);
        register("PacketPlayInCustomPayload", PacketType.Play.In.CUSTOM_PAYLOAD);
        register("PacketPlayInDifficultyChange", PacketType.Play.In.DIFFICULTY_CHANGE);
        register("PacketPlayInDifficultyLock", PacketType.Play.In.DIFFICULTY_LOCK);
        register("PacketPlayInEnchantItem", PacketType.Play.In.ENCHANT_ITEM);
        register("PacketPlayInEntityAction", PacketType.Play.In.ENTITY_ACTION);
        register("PacketPlayInEntityNBTQuery", PacketType.Play.In.ENTITY_NBT_QUERY);
        register("PacketPlayInFlying", PacketType.Play.In.FLYING);
        register("PacketPlayInHeldItemSlot", PacketType.Play.In.HELD_ITEM_SLOT);
        register("PacketPlayInChat", PacketType.Play.In.CHAT);
        register("PacketPlayInItemName", PacketType.Play.In.ITEM_NAME);
        register("PacketPlayInJigsawGenerate", PacketType.Play.In.JIGSAW_GENERATE);
        register("PacketPlayInKeepAlive", PacketType.Play.In.KEEP_ALIVE);
        register("PacketPlayInPickItem", PacketType.Play.In.PICK_ITEM);
        register("PacketPlayInRecipeDisplayed", PacketType.Play.In.RECIPE_DISPLAYED);
        register("PacketPlayInRecipeSettings", PacketType.Play.In.RECIPE_SETTINGS);
        register("PacketPlayInResourcePackStatus", PacketType.Play.In.RESOURCE_PACK_STATUS);
        register("PacketPlayInSetCommandBlock", PacketType.Play.In.SET_COMMAND_BLOCK);
        register("PacketPlayInSetCommandMinecart", PacketType.Play.In.SET_COMMAND_MINECART);
        register("PacketPlayInSetCreativeSlot", PacketType.Play.In.SET_CREATIVE_SLOT);
        register("PacketPlayInSetJigsaw", PacketType.Play.In.SET_JIGSAW);
        register("PacketPlayInSettings", PacketType.Play.In.SETTINGS);
        register("PacketPlayInSpectate", PacketType.Play.In.SPECTATE);
        register("PacketPlayInSteerVehicle", PacketType.Play.In.STEER_VEHICLE);
        register("PacketPlayInStruct", PacketType.Play.In.STRUCT);
        register("PacketPlayInTabComplete", PacketType.Play.In.TAB_COMPLETE);
        register("PacketPlayInTeleportAccept", PacketType.Play.In.TELEPORT_ACCEPT);
        register("PacketPlayInTileNBTQuery", PacketType.Play.In.TILE_NBT_QUERY);
        register("PacketPlayInTransaction", PacketType.Play.In.TRANSACTION);
        register("PacketPlayInTrSel", PacketType.Play.In.TR_SEL);
        register("PacketPlayInUpdateSign", PacketType.Play.In.UPDATE_SIGN);
        register("PacketPlayInUseEntity", PacketType.Play.In.USE_ENTITY);
        register("PacketPlayInUseItem", PacketType.Play.In.USE_ITEM);
        register("PacketPlayInVehicleMove", PacketType.Play.In.VEHICLE_MOVE);
        register("PacketPlayInWindowClick", PacketType.Play.In.WINDOW_CLICK);
        //</editor-fold>
    }

    public static void register(Class<?> clazz, PacketType packet) {
        REGISTRY.put(clazz, packet);
        REVERSE_REGISTRY.put(packet, clazz);
    }

    public static void register(String clazz, PacketType packet) {
        register(Reflection.getNMSClass(clazz), packet);
    }

    public static PacketType get(Object o) {
        return get(o.getClass());
    }

    public static PacketType get(Class<?> o) {
        return REGISTRY.get(o);
    }

    public static Object getNew(PacketType type) {
        Class<?> clazz = get(type);
        ConstructorInvoker<?> constructor = Reflection.getConstructor(clazz);
        return constructor.invoke();
    }

    public static Class<?> get(PacketType type) {
        return REVERSE_REGISTRY.get(type);
    }
}
