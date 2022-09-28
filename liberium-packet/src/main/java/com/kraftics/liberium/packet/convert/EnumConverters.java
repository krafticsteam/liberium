package com.kraftics.liberium.packet.convert;

import com.kraftics.liberium.packet.reflection.FieldAccessor;
import com.kraftics.liberium.packet.reflection.Reflection;
import com.kraftics.liberium.packet.reflection.ReflectionUtil;
import org.bukkit.SoundCategory;

public interface EnumConverters {
    enum BossAction {
        ADD,
        REMOVE,
        UPDATE_PCT,
        UPDATE_NAME,
        UPDATE_STYLE,
        UPDATE_PROPERTIES
    }

    enum BossColor {
        PINK,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        WHITE
    }

    enum BossStyle {
        PROGRESS,
        NOTCHED_6,
        NOTCHED_10,
        NOTCHED_12,
        NOTCHED_20
    }

    enum PlayerDigType {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_ITEM_WITH_OFFHAND
    }

    enum CombatEventType {
        ENTER_COMBAT,
        END_COMBAT,
        ENTITY_DIED
    }

    enum TitleAction {
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        CLEAR,
        RESET
    }

    enum WorldBorderAction {
        SET_SIZE,
        LERP_SIZE,
        SET_CENTER,
        INITIALIZE,
        SET_WARNING_TIME,
        SET_WARNING_BLOCKS
    }

    enum PlayerAction {
        SET_SIZE,
        LERP_SIZE,
        SET_CENTER,
        INITIALIZE,
        SET_WARNING_TIME,
        SET_WARNING_BLOCKS
    }

    enum ClientCommand {
        PERFORM_RESPAWN,
        REQUEST_STATS
    }

    enum ResourcePackStatus {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED
    }

    enum EntityUseAction {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }

    FieldAccessor<?> BOSS_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayOutBoss"), "Action", Enum.class);
    FieldAccessor<?> BOSS_COLOR_FIELD = Reflection.getField(ReflectionUtil.getBossBattleClass(), "BarColor", Enum.class);
    FieldAccessor<?> BOSS_STYLE_FIELD = Reflection.getField(ReflectionUtil.getBossBattleClass(), "BarStyle", Enum.class);
    FieldAccessor<?> PLAYER_DIG_TYPE_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayInBlockDig"), "EnumPlayerDigType", Enum.class);
    FieldAccessor<?> COMBAT_EVENT_TYPE_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayOutCombatEvent"), "EnumCombatEventType", Enum.class);
    FieldAccessor<?> TITLE_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayOutTitle"), "EnumTitleAction", Enum.class);
    FieldAccessor<?> WORLD_BORDER_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayOutWorldBorder"), "EnumWorldBorderAction", Enum.class);
    FieldAccessor<?> CLIENT_COMMAND_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayInClientCommand"), "EnumClientCommand", Enum.class);
    FieldAccessor<?> PLAYER_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayInEntityAction"), "EnumPlayerAction", Enum.class);
    FieldAccessor<?> RESOURCE_PACK_STATUS_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayInResourcePackStatus"), "EnumResourcePackStatus", Enum.class);
    FieldAccessor<?> ENTITY_USE_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayInUseEntity"), "EnumEntityUseAction", Enum.class);
    Class<?> SOUND_CATEGORY_CLASS = Reflection.getNMSClass("SoundCategory");

    static Class<?> getClass(FieldAccessor<?> field) {
        return field.get(null).getClass();
    }

    static EnumConverter<BossAction> getBossActionConverter() {
        return new EnumConverter<>(getClass(BOSS_ACTION_FIELD), BossAction.class);
    }

    static EnumConverter<BossColor> getBossColorConverter() {
        return new EnumConverter<>(getClass(BOSS_COLOR_FIELD), BossColor.class);
    }

    static EnumConverter<BossStyle> getBossStyleConverter() {
        return new EnumConverter<>(getClass(BOSS_STYLE_FIELD), BossStyle.class);
    }

    static EnumConverter<PlayerDigType> getPlayerDigTypeConverter() {
        return new EnumConverter<>(getClass(PLAYER_DIG_TYPE_FIELD), PlayerDigType.class);
    }

    static EnumConverter<CombatEventType> getCombatEventTypeConverter() {
        return new EnumConverter<>(getClass(COMBAT_EVENT_TYPE_FIELD), CombatEventType.class);
    }

    static EnumConverter<SoundCategory> getSoundCategoryConverter() {
        return new EnumConverter<>(SOUND_CATEGORY_CLASS, SoundCategory.class);
    }

    static EnumConverter<TitleAction> getTitleActionConverter() {
        return new EnumConverter<>(getClass(TITLE_ACTION_FIELD), TitleAction.class);
    }

    static EnumConverter<WorldBorderAction> getWorldBorderActionConverter() {
        return new EnumConverter<>(getClass(WORLD_BORDER_ACTION_FIELD), WorldBorderAction.class);
    }

    static EnumConverter<ClientCommand> getClientCommandConverter() {
        return new EnumConverter<>(getClass(CLIENT_COMMAND_FIELD), ClientCommand.class);
    }

    static EnumConverter<PlayerAction> getPlayerActionConverter() {
        return new EnumConverter<>(getClass(PLAYER_ACTION_FIELD), PlayerAction.class);
    }

    static EnumConverter<ResourcePackStatus> getResourcePackStatusConverter() {
        return new EnumConverter<>(getClass(RESOURCE_PACK_STATUS_FIELD), ResourcePackStatus.class);
    }

    static EnumConverter<EntityUseAction> getEntityUseActionConverter() {
        return new EnumConverter<>(getClass(ENTITY_USE_ACTION_FIELD), EntityUseAction.class);
    }

    class EnumConverter<T extends Enum<T>> implements ObjectConverter<T> {
        private final Class<?> genericType;
        private final Class<T> specificType;

        public EnumConverter(Class<?> genericType, Class<T> specificType) {
            this.genericType = genericType;
            this.specificType = specificType;
        }

        @Override
        public T getSpecific(Object generic) {
            return Enum.valueOf(specificType, ((Enum<?>) generic).name());
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object getGeneric(T specific) {
            return Enum.valueOf((Class) genericType, specific.name());
        }

        @Override
        public Class<?> getGenericType() {
            return genericType;
        }

        @Override
        public Class<T> getSpecificType() {
            return specificType;
        }
    }
}
