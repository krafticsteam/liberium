package com.kraftics.liberium.packet.convert;

import com.kraftics.liberium.packet.reflection.ConstructorInvoker;
import com.kraftics.liberium.packet.reflection.FieldAccessor;
import com.kraftics.liberium.packet.reflection.Reflection;

public class BlockPositionConverter implements ObjectConverter<BlockPosition> {
    public static final Class<?> NMS = Reflection.getNMSClass("BlockPosition");
    public static final ConstructorInvoker<?> CONSTRUCTOR = Reflection.getConstructor(NMS, int.class, int.class, int.class);

    private static final FieldAccessor<Integer> x = Reflection.getField(NMS, int.class, 0);
    private static final FieldAccessor<Integer> y = Reflection.getField(NMS, int.class, 1);
    private static final FieldAccessor<Integer> z = Reflection.getField(NMS, int.class, 2);

    @Override
    public BlockPosition getSpecific(Object generic) {
        return new BlockPosition(x.get(generic), y.get(generic), z.get(generic));
    }

    @Override
    public Object getGeneric(BlockPosition specific) {
        return CONSTRUCTOR.invoke(specific.getX(), specific.getY(), specific.getZ());
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<BlockPosition> getSpecificType() {
        return BlockPosition.class;
    }
}
