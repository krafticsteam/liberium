package com.kraftics.krafticslib;

import com.kraftics.krafticslib.bukkit.BukkitKrafticsLibAPI;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to create or manage apis
 *
 * @author Panda885
 */
public class KrafticsLib {
    private static final Set<KrafticsLibAPI> apis = new HashSet<>();

    /**
     * Creates new {@link KrafticsLibAPI} for bukkit
     *
     * @param plugin The plugin using this api
     * @return The api
     */
    @NotNull
    public static KrafticsLibAPI create(Plugin plugin) {
        KrafticsLibAPI api = new BukkitKrafticsLibAPI(plugin);
        apis.add(api);
        return api;
    }

    /**
     * Returns all apis that got created
     * This only works if you use the {@link KrafticsLib#create(Plugin) create} method
     *
     * @return set of apis
     */
    public static Set<KrafticsLibAPI> getApis() {
        return apis;
    }
}
