package com.kraftics.liberium;

import com.kraftics.liberium.bukkit.BukkitLiberiumAPI;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to create or manage apis
 *
 * @author Panda885
 */
public class Liberium {
    private static final Set<LiberiumAPI> apis = new HashSet<>();

    /**
     * Creates new {@link LiberiumAPI} for bukkit
     *
     * @param plugin The plugin using this api
     * @return The api
     */
    @NotNull
    public static LiberiumAPI create(Plugin plugin) {
        LiberiumAPI api = new BukkitLiberiumAPI(plugin);
        apis.add(api);
        return api;
    }

    /**
     * Returns all apis that got created
     * This only works if you use the {@link Liberium#create(Plugin) create} method
     *
     * @return set of apis
     */
    public static Set<LiberiumAPI> getApis() {
        return apis;
    }
}
