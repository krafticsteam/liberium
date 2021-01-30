package com.kraftics.krafticslib;

import org.bukkit.plugin.java.JavaPlugin;

public class KrafticsLib extends JavaPlugin {

    private Metrics metrics;

    @Override
    public void onEnable() {
        metrics = new Metrics(this, 9916);
    }
}
