package com.kraftics.krafticslib.packet.convert;

import com.kraftics.krafticslib.packet.reflection.MethodInvoker;
import com.kraftics.krafticslib.packet.reflection.Reflection;
import com.kraftics.krafticslib.packet.reflection.ReflectionUtil;
import org.bukkit.Material;

public class BlockConverter implements ObjectConverter<Material> {
    public static final Class<?> NMS = Reflection.getNMSClass("Block");
    private static final MethodInvoker<Material> getMaterial = Reflection.getMethod(ReflectionUtil.getCraftMagicNumberClass(), "getMaterial", Material.class, NMS);
    private static final MethodInvoker<?> getBlock = Reflection.getMethod(ReflectionUtil.getCraftMagicNumberClass(), "getBlock", NMS, Material.class);

    @Override
    public Material getSpecific(Object generic) {
        return getMaterial.invoke(null, generic);
    }

    @Override
    public Object getGeneric(Material specific) {
        return getBlock.invoke(null, specific);
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<Material> getSpecificType() {
        return Material.class;
    }
}
