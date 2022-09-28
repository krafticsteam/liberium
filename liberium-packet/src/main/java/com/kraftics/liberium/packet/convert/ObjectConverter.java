package com.kraftics.liberium.packet.convert;

public interface ObjectConverter<T> {

    T getSpecific(Object generic);

    Object getGeneric(T specific);

    Class<?> getGenericType();

    Class<T> getSpecificType();
}
