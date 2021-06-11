package com.kraftics.krafticslib;

import com.kraftics.krafticslib.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class KrafticsLibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 9916);
    }
}
