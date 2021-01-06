package com.kraftics.krafticslib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private boolean log;
    private boolean defaults;

    /**
     * Constructs the configuration
     *
     * @param plugin Plugin instance using the configuration
     * @param file Configuration file
     * @param log If errors should be logged thought the plugin logger
     * @param defaults If should load defaults from the resource class loader
     */
    public Config(JavaPlugin plugin, File file, boolean log, boolean defaults) {
        this.plugin = plugin;
        this.file = file;
        this.log = log;
        this.defaults = defaults;

        load();
    }

    /**
     * Constructs the configuration
     *
     * @param plugin Plugin instance using the configuration
     * @param file Configuration file
     */
    public Config(JavaPlugin plugin, File file) {
        this(plugin, file, true, true);
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
        } catch (Exception e) {
            if (log) plugin.getLogger().log(Level.SEVERE, "Could not save config " + file.getName(), e);
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

            try {
                InputStream resource = plugin.getResource(file.getName());
                if (resource != null) setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(resource)));
            } catch (Exception e) {
                if (log) plugin.getLogger().log(Level.SEVERE, "Could not load defaults from config " + file.getName(), e);
                return false;
            }

            return true;
        } catch (Exception e) {
            if (log) plugin.getLogger().log(Level.SEVERE, "Could not load config " + file.getName(), e);
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
