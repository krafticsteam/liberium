package com.kraftics.krafticslib.packet.convert;

import com.kraftics.krafticslib.packet.reflection.ConstructorInvoker;
import com.kraftics.krafticslib.packet.reflection.MethodInvoker;
import com.kraftics.krafticslib.packet.reflection.Reflection;

public class MinecraftKeyConverter implements ObjectConverter<MinecraftKey> {
    public static final Class<?> NMS = Reflection.getNMSClass("MinecraftKey");

    private static final ConstructorInvoker<?> CONSTRUCTOR = Reflection.getConstructor(NMS, String.class, String.class);
    private static final MethodInvoker<String> namespace = Reflection.getMethod(NMS, "getNamespace", String.class);
    private static final MethodInvoker<String> key = Reflection.getMethod(NMS, "getKey", String.class);

    @Override
    public MinecraftKey getSpecific(Object generic) {
        return new MinecraftKey(namespace.invoke(generic), key.invoke(generic));
    }

    @Override
    public Object getGeneric(MinecraftKey specific) {
        return CONSTRUCTOR.invoke(specific.getNamespace(), specific.getKey());
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<MinecraftKey> getSpecificType() {
        return MinecraftKey.class;
    }
}
