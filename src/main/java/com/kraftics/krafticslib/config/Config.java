package com.kraftics.krafticslib.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfigurationOptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * Custom configuration containing file with the configuration
 * and some useful settings/methods.
 *
 * @since v0.3.0
 * @see FileConfiguration
 * @author Panda885
 */
public abstract class Config extends FileConfiguration {
    protected JavaPlugin plugin;
    protected File file;

    protected boolean log;

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
    public Config(@Nonnull JavaPlugin plugin, @Nonnull File file, boolean log, boolean defaults, @Nonnull Supplier<FileConfiguration> supp) {
        this.plugin = plugin;
        this.file = file;
        this.log = log;
        this.original = supp.get();

        options().copyDefaults(defaults);
        options().copyHeader(true);
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
     * Loads data from the file.
     * Also loads defaults if they are enabled.
     *
     * @return True if loading was successful
     */
    public boolean load() {
        try {
            load(file);

            if (!options().copyDefaults()) return true;

            try {
                InputStream resource = plugin.getResource(file.getName());
                if (resource != null) setDefaults(createDefaults(resource));
                return true;
            } catch (Exception e) {
                if (log) plugin.getLogger().log(Level.SEVERE, "Could not load defaults from config " + file.getName(), e);
                return false;
            }
        } catch (Exception e) {
            if (log) plugin.getLogger().log(Level.SEVERE, "Could not load config " + file.getName(), e);
            return false;
        }
    }

    /**
     * Copies options from one configuration options to another.
     *
     * @param from From configration
     * @param to To configuration
     */
    protected void copyOptions(FileConfiguration from, FileConfiguration to) {
        to.options().copyDefaults(from.options().copyDefaults());
        to.options().copyHeader(from.options().copyHeader());
        to.options().header(from.options().header());
        to.options().pathSeparator(from.options().pathSeparator());

        Configuration defaults = from.getDefaults();
        if (defaults == null) return;
        to.setDefaults(defaults);
    }

    /**
     * Saves data to string
     *
     * @return the data
     */
    @Override
    @Nonnull
    public String saveToString() {
        copyOptions(this, original);
        return original.saveToString();
    }

    /**
     * Loads data from string
     *
     * @param contents String containing the data
     * @throws InvalidConfigurationException If could not load the data
     */
    @Override
    public void loadFromString(@Nonnull String contents) throws InvalidConfigurationException {
        original.loadFromString(contents);
    }

    /**
     * <strong>UNSUPPORTED</strong>,
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
        return options().copyDefaults();
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
        options().copyDefaults(defaults);
    }

    public boolean createFile() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            file.createNewFile();
            return true;
        } catch (IOException e) {
            if (log) plugin.getLogger().log(Level.SEVERE, "Could not create file " + file.getName(), e);
            return false;
        }
    }
}
