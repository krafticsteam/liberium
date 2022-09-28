package com.kraftics.liberium.packet.convert;

import com.kraftics.liberium.packet.reflection.MethodInvoker;
import com.kraftics.liberium.packet.reflection.Reflection;
import com.kraftics.liberium.packet.reflection.ReflectionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackConverter implements ObjectConverter<ItemStack> {
    public static final Class<?> NMS = Reflection.getNMSClass("ItemStack");
    public static final Class<?> CRAFT = Reflection.getCraftClass("inventory.CraftItemStack");
    public static final MethodInvoker<ItemStack> CRAFT_MIRROR_METHOD = Reflection.getMethod(CRAFT, "asCraftMirror", ItemStack.class, NMS);
    public static final MethodInvoker<?> NMS_COPY_METHOD = Reflection.getMethod(CRAFT, "asNMSCopy", NMS, ItemStack.class);

    @Override
    public ItemStack getSpecific(Object generic) {
        if (generic == null) return new ItemStack(Material.AIR);
        return CRAFT_MIRROR_METHOD.invoke(null, generic);
    }

    @Override
    public Object getGeneric(ItemStack specific) {
        if (ReflectionUtil.is(CRAFT, specific)) {
            return ReflectionUtil.unwrap(specific);
        }
        return NMS_COPY_METHOD.invoke(null, specific);
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<ItemStack> getSpecificType() {
        return ItemStack.class;
    }
}
