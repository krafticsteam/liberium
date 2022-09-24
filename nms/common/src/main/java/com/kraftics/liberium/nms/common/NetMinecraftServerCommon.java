package com.kraftics.liberium.nms.common;

import com.kraftics.liberium.nms.NetMinecraftServer;
import com.kraftics.liberium.nms.common.command.CommandNodeConverter;
import com.kraftics.liberium.reflection.ObjectWrapper;
import com.kraftics.liberium.reflection.exception.ReflectionException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

public class NetMinecraftServerCommon implements NetMinecraftServer {
    private final MinecraftServer server;

    public NetMinecraftServerCommon(MinecraftServer server) {
        this.server = server;
    }

    public static NetMinecraftServer fromBukkit(Server server) throws ReflectionException {
        return new NetMinecraftServerCommon(ObjectWrapper.wrap(server).invoke("getServer").cast(MinecraftServer.class));
    }

    @Override
    public CommandMap getCommandMap() {
        return server.server.getCommandMap();
    }

    @Override
    public CommandDispatcher<Object> getServerDispatcher() {
        return null; //server.vanillaCommandDispatcher.a();
    }

    @Override
    public void registerConvertedCommand(CommandNode<CommandSender> node) {
        server.vanillaCommandDispatcher.a().getRoot().addChild(CommandNodeConverter.convert(node));
    }

    @Override
    public void registerConvertedCommand(LiteralArgumentBuilder<CommandSender> builder) {
        server.vanillaCommandDispatcher.a().register(CommandNodeConverter.convert(builder));
    }
}
