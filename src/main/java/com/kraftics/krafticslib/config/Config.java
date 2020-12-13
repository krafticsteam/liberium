package com.kraftics.krafticslib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Custom yaml configuration
 *
 * @see YamlConfiguration
 * @author Panda885
 */
public class Config extends YamlConfiguration {
    private JavaPlugin plugin;
    private File file;

    /**
     * Constructs the config
     *
     * @param plugin plugin instance using the config
     * @param file config file
     */
    public Config(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;

        load();
    }

    /**
     * Saves the config to the file
     *
     * @return true if it successfully saved
     */
    public boolean save() {
        try {
            save(file);
            return true;
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config " + file.getName());
            return false;
        }
    }

    /**
     * Loads the config from the file
     *
     * @return true if it successfully loaded
     */
    public boolean load() {
        try {
            load(file);
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load config " + file.getName());
            return false;
        }
    }

    /**
     * Gets the plugin using the config
     *
     * @return the plugin
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets the config file
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }
}
