package com.kraftics.krafticslib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * Custom configuration
 *
 * @since v0.3.0
 * @see FileConfiguration
 * @author Panda885
 */
public abstract class Config extends FileConfiguration {
    protected JavaPlugin plugin;
    protected File file;

    protected boolean log;
    protected boolean defaults;

    protected FileConfiguration original;

    /**
     * Constructs custom configuration
     *
     * @param plugin The plugin using the configuration, used for logging
     * @param file The file from which it loads and saves data
     * @param log If logging should be enabled
     * @param defaults If should load defaults from class-path
     * @param supp A supplier generating the original {@link FileConfiguration}
     */
    public Config(@Nonnull JavaPlugin plugin, @Nonnull File file, boolean log, boolean defaults,
                  @Nonnull Supplier<FileConfiguration> supp) {
        this.plugin = plugin;
        this.file = file;
        this.log = log;
        this.defaults = defaults;
        this.original = supp.get();
    }

    /**
     * Creates defaults from t h e  s t r e a m
     *
     * @param stream T h e   s t r e a m
     * @return The defaults
     */
    @Nonnull
    protected abstract FileConfiguration createDefaults(InputStream stream);

    /**
     * Saves data to the file
     *
     * @return True if saving was successful
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
     * Loads data from the file
     * Also loads defaults if they are enabled
     *
     * @return True if loading was successful
     */
    public boolean load() {
        try {
            load(file);

            if (!defaults) return true;

            try {
                InputStream resource = plugin.getResource(file.getName());
                if (resource != null)
                    setDefaults(createDefaults(resource));
                return true;
            } catch (Exception e) {
                if (log)
                    plugin.getLogger().log(Level.SEVERE, "Could not load defaults from config " + file.getName(), e);
                return false;
            }
        } catch (Exception e) {
            if (log) plugin.getLogger().log(Level.SEVERE, "Could not load config " + file.getName(), e);
            return false;
        }
    }

    @Override
    @Nonnull
    public String saveToString() {
        return original.saveToString();
    }

    @Override
    public void loadFromString(@Nonnull String contents) throws InvalidConfigurationException {
        original.loadFromString(contents);
    }

    /**
     * <strong>UNSUPPORTED</strong>,
     * use {@link Config#original()}.{@link FileConfiguration#buildHeader() buildHeader()}
     */
    @Override
    @Nonnull
    @Deprecated
    protected String buildHeader() {
        throw new UnsupportedOperationException("Use config.original().buildHeader()");
    }

    /**
     * @return The original file configuration
     */
    public FileConfiguration original() {
        return original;
    }

    /**
     * @return The plugin using this config
     */
    public JavaPlugin plugin() {
        return plugin;
    }

    /**
     * @return File of this config
     */
    public File file() {
        return file;
    }

    /**
     * @return If logging is enabled
     */
    public boolean log() {
        return log;
    }

    /**
     * @return If defaults are enabled
     */
    public boolean defaults() {
        return defaults;
    }

    /**
     * @param log If logging should be enabled
     */
    public void log(boolean log) {
        this.log = log;
    }

    /**
     * @param defaults If defaults should be enabled
     */
    public void defaults(boolean defaults) {
        this.defaults = defaults;
    }
}
