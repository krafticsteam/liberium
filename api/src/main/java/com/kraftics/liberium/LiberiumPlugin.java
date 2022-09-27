package com.kraftics.liberium;

import com.kraftics.liberium.command.CommandManager;
import com.kraftics.liberium.nms.NetMinecraftServer;
import com.kraftics.liberium.nms.NetMinecraftServerProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class LiberiumPlugin extends JavaPlugin {
    private final NetMinecraftServer netMinecraftServer = NetMinecraftServerProvider.get(getServer());
    private final CommandManager commandManager = new CommandManager(this);

    public LiberiumPlugin() {
        super();

        onInitialize();
    }

    public abstract void onInitialize();

    @NotNull
    public NetMinecraftServer getNetMinecraftServer() {
        return netMinecraftServer;
    }

    @NotNull
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
