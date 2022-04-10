package com.kraftics.liberium.dependency;

import java.util.HashMap;
import java.util.Map;

public class DefaultInstanceContainer implements InstanceContainer {
    private final Map<Class<?>, Object> map = new HashMap<>();

    @Override
    public Object getInstance(Class<?> type) {
        return map.get(type);
    }

    @Override
    public void setInstance(Class<?> type, Object object) {
        map.put(type, object);
    }

    @Override
    public boolean hasInstance(Class<?> type) {
        return map.containsKey(type);
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    @Override
    public void configureProcess(DependencyProcessor<?> processor) {

    }
}
