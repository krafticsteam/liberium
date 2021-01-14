package com.kraftics.krafticslib;

import com.kraftics.krafticslib.update.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public class KrafticsLibPlugin extends JavaPlugin {

    private Metrics metrics;
    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        updateChecker = new UpdateChecker(this, true);
        metrics = new Metrics(this, 9916);

        updateChecker.check();
    }
}
