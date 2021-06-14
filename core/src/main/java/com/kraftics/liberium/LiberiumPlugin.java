package com.kraftics.liberium;

import com.kraftics.liberium.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class LiberiumPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 9916);
    }
}
