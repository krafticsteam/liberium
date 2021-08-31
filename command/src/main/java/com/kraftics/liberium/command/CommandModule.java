package com.kraftics.liberium.command;

import com.kraftics.liberium.module.Module;
import com.kraftics.liberium.packet.reflection.MethodInvoker;
import com.kraftics.liberium.packet.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class CommandModule extends Module {
    private CommandDispatcher dispatcher;

    @Override
    public void onInit() {
        dispatcher = new CommandDispatcher(plugin.getName().toLowerCase(Locale.ROOT), getCommandMap());
    }

    @Override
    public void onEnable() {
        registerAnnotation(Command.class, this::onCommand, null, null);
   }

    @Override
    public void onDisable() {
        unregisterAnnotation(Command.class);
    }

    private void onCommand(Annotation annotation, Object component, Method method) {
        System.out.println("Registering command: " + method.getName());

        if (!method.isAccessible())
            method.setAccessible(true);

        Command commandAnn = (Command) annotation;
        String name = commandAnn.value();

        if (method.getParameterCount() != 1 || method.getParameterTypes()[0] != CommandBuilder.class)
            return;

        dispatcher.register(name, builder -> {
            try {
                method.invoke(component, builder);
            } catch (IllegalAccessException | IllegalArgumentException | ExceptionInInitializerError | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public CommandDispatcher getDispatcher() {
        return dispatcher;
    }

    private CommandMap getCommandMap() {
        Class<?> craftServerClass = Reflection.getCraftClass("CraftServer");
        Object craftServer = craftServerClass.cast(Bukkit.getServer());
        MethodInvoker<CommandMap> method = Reflection.getMethod(craftServerClass, "getCommandMap", CommandMap.class);
        return method.invoke(craftServer);
    }
}
