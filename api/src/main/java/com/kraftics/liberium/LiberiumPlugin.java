package com.kraftics.liberium;

import com.kraftics.liberium.annotation.AnnotationProcessor;
import com.kraftics.liberium.annotation.WrapperAnnotationProcessor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LiberiumPlugin extends JavaPlugin {
    protected final AnnotationProcessor annotationProcessor = new WrapperAnnotationProcessor();

    public LiberiumPlugin() {
        super();

        onInit();
    }

    public void onInit() {

    }

    public AnnotationProcessor getAnnotationProcessor() {
        return annotationProcessor;
    }
}
