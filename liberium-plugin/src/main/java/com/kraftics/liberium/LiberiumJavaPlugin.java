package com.kraftics.liberium;

import com.kraftics.liberium.command.CommandModule;
import com.kraftics.liberium.metrics.Metrics;
import com.kraftics.liberium.module.ModuleRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class LiberiumJavaPlugin extends JavaPlugin {

    public LiberiumJavaPlugin() {
        super();

        ModuleRegistry.register(CommandModule.class, CommandModule::new);
    }

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 9916);
    }
}
