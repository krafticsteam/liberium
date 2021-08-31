package com.kraftics.liberium.module.main;

import com.kraftics.liberium.OnInit;
import com.kraftics.liberium.PluginInstance;
import com.kraftics.liberium.ServerInstance;
import com.kraftics.liberium.module.Module;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainModule extends Module {

    @Override
    public void onInit() {

    }

    @Override
    public void onEnable() {
        registerFieldAnnotation(PluginInstance.class, (o) -> getPlugin());
        registerFieldAnnotation(ServerInstance.class, (o) -> getPlugin().getServer());
        registerAnnotation(OnInit.class, this::onInitAnnotation, null, null);
    }

    @Override
    public void onDisable() {
        unregisterAnnotation(PluginInstance.class);
        unregisterAnnotation(ServerInstance.class);
        unregisterAnnotation(OnInit.class);
    }

    @Override
    public void onComponentRegistry(Object component) {

    }

    private void onInitAnnotation(Annotation annotation, Object component, Method method) {
        if (!method.isAccessible())
            method.setAccessible(true);

        if (method.getParameterCount() != 0)
            return;

        try {
            method.invoke(component);
        } catch (IllegalAccessException | IllegalArgumentException | ExceptionInInitializerError | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
