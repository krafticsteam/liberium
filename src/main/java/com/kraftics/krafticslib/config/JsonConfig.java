package com.kraftics.krafticslib.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * Custom json configuration
 *
 * @since v0.3.0-beta.2
 * @see JsonConfiguration
 * @author Panda885
 */
public class JsonConfig extends Config {
    /**
     * Constructs custom configuration
     *
     * @param plugin   The plugin using the configuration, used for logging
     * @param file     The file from which it loads and saves data
     * @param log      If logging should be enabled
     * @param defaults If should load defaults from class-path
     */
    public JsonConfig(@Nonnull JavaPlugin plugin, @Nonnull File file, boolean log, boolean defaults) {
        super(plugin, file, log, defaults, JsonConfiguration::new);
    }

    @Nonnull
    @Override
    protected FileConfiguration createDefaults(InputStream stream) {
        return JsonConfiguration.loadConfiguration(new InputStreamReader(stream));
    }
}
