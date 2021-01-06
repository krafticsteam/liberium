package com.kraftics.krafticslib.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {
    private Gson gson = new Gson();

    @Override
    @Nonnull
    public String saveToString() {
        return gson.toJson(getValues(false));
    }

    @Override
    public void loadFromString(@Nonnull String contents) throws InvalidConfigurationException {
        try {
            Map<?, ?> map = gson.fromJson(contents, Map.class);

            if (map != null) {
                fromMap(map, this);
            }
        } catch (JsonSyntaxException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    protected void fromMap(Map<?, ?> map, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                fromMap((Map<?, ?>) value, section);
            } else {
                section.set(key, value);
            }
        }
    }

    @Override
    @Nonnull
    protected String buildHeader() {
        return "";
    }
}
