package com.kraftics.liberium.main;

import com.kraftics.liberium.module.Module;

public class MainModule extends Module {

    @Override
    public void onInit() {
        registerAnnotation(PluginInstance.class).onField(plugin);
        registerAnnotation(ServerInstance.class).onField(getPlugin()::getServer);
        registerAnnotation(OnInit.class).onMethodExecute();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
