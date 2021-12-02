package com.kraftics.liberium.command;

import com.kraftics.liberium.module.Module;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

public class CommandModule extends Module {
    private CommandDispatcher<CommandSender> dispatcher;

    @Override
    public void onInit() {
        dispatcher = new CommandDispatcher<>();

        registerAnnotation(Command.class).onMethod(this::onCommand);
        registerAnnotation(CommandDispatcherInstance.class).onField(dispatcher);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @SuppressWarnings("unchecked")
    private void onCommand(Command command, Method method, Object component) {
        try {
            method.setAccessible(true);

            LiteralArgumentBuilder<CommandSender> builder = LiteralArgumentBuilder.literal(command.name());
            if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == LiteralArgumentBuilder.class) {
                if (method.getReturnType() == LiteralArgumentBuilder.class) {
                    builder = (LiteralArgumentBuilder<CommandSender>) method.invoke(component, builder);
                } else {
                    method.invoke(component, builder);
                }
            } else if (method.getParameterCount() == 0 && method.getReturnType() == LiteralArgumentBuilder.class) {
                builder = (LiteralArgumentBuilder<CommandSender>) method.invoke(component);
            } else {
                throw new IllegalStateException("Method " + method + " has invalid arguments for a command");
            }

            // TODO: Register to bukkit's command map
            dispatcher.register(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandDispatcher<CommandSender> getDispatcher() {
        return dispatcher;
    }

    /*
    private CommandMap getCommandMap() {
        Class<?> craftServerClass = Reflection.getCraftClass("CraftServer");
        Object craftServer = craftServerClass.cast(Bukkit.getServer());
        MethodInvoker<CommandMap> method = Reflection.getMethod(craftServerClass, "getCommandMap", CommandMap.class);
        return method.invoke(craftServer);
    }*/
}
