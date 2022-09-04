package com.kraftics.liberium;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class LiberiumPlugin extends JavaPlugin {

    public LiberiumPlugin() {
        super();

        onInitialize();
    }

    public abstract void onInitialize();
}
