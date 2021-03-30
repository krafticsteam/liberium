package com.kraftics.krafticslib.packet.convert;

import com.kraftics.krafticslib.packet.reflection.FieldAccessor;
import com.kraftics.krafticslib.packet.reflection.Reflection;
import com.kraftics.krafticslib.packet.reflection.ReflectionUtil;

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

    FieldAccessor<?> BOSS_ACTION_FIELD = Reflection.getField(Reflection.getNMSClass("PacketPlayOutBoss"), "Action", Enum.class);
    FieldAccessor<?> BOSS_COLOR_FIELD = Reflection.getField(ReflectionUtil.getBossBattleClass(), "BarColor", Enum.class);
    FieldAccessor<?> BOSS_STYLE_FIELD = Reflection.getField(ReflectionUtil.getBossBattleClass(), "BarStyle", Enum.class);

    static Class<?> getBossStyleClass() {
        return BOSS_STYLE_FIELD.get(null).getClass();
    }

    static Class<?> getBossColorClass() {
        return BOSS_COLOR_FIELD.get(null).getClass();
    }

    static Class<?> getBossActionClass() {
        return BOSS_ACTION_FIELD.get(null).getClass();
    }

    static EnumConverter<BossAction> getBossActionConverter() {
        return new EnumConverter<>(getBossActionClass(), BossAction.class);
    }

    static EnumConverter<BossColor> getBossColorConverter() {
        return new EnumConverter<>(getBossColorClass(), BossColor.class);
    }

    static EnumConverter<BossStyle> getBossStyleConverter() {
        return new EnumConverter<>(getBossStyleClass(), BossStyle.class);
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
