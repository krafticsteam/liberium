package com.kraftics.liberium.command;

import com.kraftics.liberium.nms.NetMinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private final List<LiberiumCommandNode> commands = new ArrayList<>();
    private final NetMinecraftServer nms;

    public CommandManager(NetMinecraftServer nms) {
        this.nms = nms;
    }

    public void registerCommand(CommandArgumentBuilder command) {
        if (getCommand(command.getLiteral()).isPresent()) throw new IllegalArgumentException("A command with name " + command.getLiteral() + " is already registered");
        LiberiumCommandNode node = command.build();
        this.commands.add(node);
        this.nms.getServerDispatcher().getRoot().addChild(this.nms.getCommandNodeConverter().convert(node));
    }

    @NotNull
    public Optional<LiberiumCommandNode> getCommand(String name) {
        return commands.stream().filter(node -> node.getName().equals(name)).findFirst(); // TODO: Check if commands are case-insensitive
    }
}
