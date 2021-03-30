package com.kraftics.krafticslib.packet.convert;

import com.kraftics.krafticslib.packet.reflection.MethodInvoker;
import com.kraftics.krafticslib.packet.reflection.Reflection;
import com.kraftics.krafticslib.packet.reflection.ReflectionUtil;
import org.bukkit.Material;

public class BlockDataConverter implements ObjectConverter<BlockData> {
    public static final Class<?> NMS = Reflection.getNMSClass("IBlockData");
    private static final MethodInvoker<?> from = Reflection.getMethod(ReflectionUtil.getCraftMagicNumberClass(), "getBlock", NMS, Material.class, byte.class);
    private static final MethodInvoker<Byte> toLegacyData = Reflection.getMethod(ReflectionUtil.getCraftMagicNumberClass(), "toLegacyData", byte.class, NMS);
    private static final MethodInvoker<?> getBlock = Reflection.getMethod(NMS, "getBlock", BlockConverter.NMS);

    private final BlockConverter converter = new BlockConverter();

    @Override
    public BlockData getSpecific(Object generic) {
        int data = toLegacyData.invoke(null, generic);
        Object block = getBlock.invoke(generic);
        Material material = converter.getSpecific(block);
        return new BlockData(material, data);
    }

    @Override
    public Object getGeneric(BlockData specific) {
        return from.invoke(null, specific.getMaterial(), (byte) specific.getData());
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<BlockData> getSpecificType() {
        return BlockData.class;
    }
}
