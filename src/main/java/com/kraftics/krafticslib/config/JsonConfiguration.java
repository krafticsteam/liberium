package com.kraftics.krafticslib.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class JsonConfiguration extends FileConfiguration {
    private Gson gson = new Gson();

//    Extended methods

    @Override
    @Nonnull
    public String saveToString() {
        return buildHeader() + gson.toJson(getMappedValues(this, false));
    }

    @Override
    public void loadFromString(@Nonnull String contents) throws InvalidConfigurationException {
        try {
            this.map.clear();

            Map<?, ?> map = gson.fromJson(contents, Map.class);

            if (map != null) {
                fromMap(map, this);
            }

            parseHeader(contents);
        } catch (JsonSyntaxException e) {
            throw new InvalidConfigurationException(e);
        }
    }

//    Utils

    protected void fromMap(Map<?, ?> map, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                fromMap((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    @Nonnull
    protected Map<String, Object> getMappedValues(@Nonnull ConfigurationSection section, boolean deep) {
        Map<String, Object> def = section.getValues(deep);
        Map<String, Object> mapped = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : def.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof ConfigurationSection) {
                ConfigurationSection second = section.getConfigurationSection(key);
                if (second == null) continue;
                mapped.put(key, getMappedValues(second, deep));
            } else {
                mapped.put(key, value);
            }
        }

        return mapped;
    }

    protected void parseHeader(String s) {
        StringBuilder builder = new StringBuilder();
        String[] lines = s.split("\r?\n", -1);

        for (String line : lines) {
            if (line.isEmpty()) continue;

            if (line.startsWith("//")) {
                builder.append(line.substring(line.charAt(2) == ' ' ? 3 : 2)).append("\n");
            } else {
                break;
            }
        }

        options().header(builder.toString());
    }

    @Override
    @Nonnull
    protected String buildHeader() {
        String header = options().header();
        if (header == null || header.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        String[] lines = header.split("\r?\n", -1);

        for (String line : lines) {
            builder.append("// ").append(line).append('\n');
        }

        return builder.toString();
    }

//    Loaders

    @Nonnull
    public static JsonConfiguration loadConfiguration(@Nonnull Reader reader) {
        Validate.notNull(reader, "Stream cannot be null");

        JsonConfiguration config = new JsonConfiguration();

        try {
            config.load(reader);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load json configuration from stream", ex);
        }

        return config;
    }

    @Nonnull
    public static JsonConfiguration loadConfiguration(@Nonnull File file) {
        Validate.notNull(file, "File cannot be null");

        JsonConfiguration config = new JsonConfiguration();

        try {
            config.load(file);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load json configuration from file", ex);
        }

        return config;
    }
}
