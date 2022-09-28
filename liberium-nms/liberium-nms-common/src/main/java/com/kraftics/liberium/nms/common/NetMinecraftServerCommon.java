package com.kraftics.liberium.nms.common;

import com.kraftics.liberium.nms.NetMinecraftServer;
import com.kraftics.liberium.nms.common.command.CommandNodeConverter;
import com.kraftics.liberium.reflection.ObjectWrapper;
import com.kraftics.liberium.reflection.exception.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

public class NetMinecraftServerCommon implements NetMinecraftServer {
    private final MinecraftServer server;

    public NetMinecraftServerCommon(MinecraftServer server) {
        this.server = server;
    }

    public static NetMinecraftServerCommon fromBukkit(Server server) throws ReflectionException {
        return new NetMinecraftServerCommon(ObjectWrapper.wrap(server).invoke("getServer").cast(MinecraftServer.class));
    }

    @Override
    public CommandMap getCommandMap() {
        return server.server.getCommandMap();
    }

    public CommandDispatcher<CommandListenerWrapper> getServerDispatcher() {
        return server.aC().a();
    }

    @Override
    public void registerConvertedCommand(CommandNode<CommandSender> node) {
        getServerDispatcher().getRoot().addChild(CommandNodeConverter.convert(node));
    }

    @Override
    public void registerConvertedCommand(LiteralArgumentBuilder<CommandSender> builder) {
        getServerDispatcher().register(CommandNodeConverter.convert(builder));
    }
}
