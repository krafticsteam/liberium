package com.kraftics.krafticslib;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;

public class KrafticsLibPlugin extends JavaPlugin {

    private Metrics metrics;

    @Override
    public void onEnable() {
        metrics = new Metrics(this, 9916);
        MongoCollection a;
    }
}
