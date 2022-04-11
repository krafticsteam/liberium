package com.kraftics.liberium.component;

import com.kraftics.liberium.dependency.InstanceContainer;
import com.kraftics.liberium.main.OnInit;

import java.util.HashMap;
import java.util.Map;

public class ComponentContainer implements InstanceContainer {
    private final Map<Class<?>, Object> instanceMap = new HashMap<>();

    @Override
    public Object getInstance(Class<?> type) {
        return instanceMap.get(type);
    }

    @Override
    public void setInstance(Class<?> type, Object object) {
        instanceMap.put(type, object);
    }

    @Override
    public boolean hasInstance(Class<?> type) {
        return instanceMap.containsKey(type);
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.isAnnotationPresent(OnInit.class);
    }

}
