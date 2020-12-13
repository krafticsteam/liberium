package eu.kraftics.krafticslib.database;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public interface DatabaseSerializable {

    Map<String, Object> serialize();

    static DatabaseSerializable from(ConfigurationSerializable serializable) {
        return serializable::serialize;
    }
}
