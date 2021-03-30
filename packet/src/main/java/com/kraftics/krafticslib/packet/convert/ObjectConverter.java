package com.kraftics.krafticslib.packet.convert;

public interface ObjectConverter<T> {

    T getSpecific(Object generic);

    Object getGeneric(T specific);

    Class<?> getGenericType();

    Class<T> getSpecificType();
}
