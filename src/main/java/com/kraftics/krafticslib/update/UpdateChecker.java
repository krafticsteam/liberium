package com.kraftics.krafticslib.update;

import com.google.gson.*;
import com.kraftics.krafticslib.KrafticsLibPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class UpdateChecker {
    private KrafticsLibPlugin plugin;
    private boolean preRelease;
    private GitHub gitHub;

    public UpdateChecker(KrafticsLibPlugin plugin, boolean preRelease) {
        this.plugin = plugin;
        this.preRelease = preRelease;
        this.gitHub = new GitHub();
    }

    public void check() {
        new Thread(() -> {
            try {
                gitHub.update();
                String currentRelease = plugin.getDescription().getVersion();
                String latestRelease = preRelease ? gitHub.latestPreRelease : gitHub.latestRelease;
                if (latestRelease == null) throw new UpdateException("There are no matching releases");
                if (!latestRelease.equals(currentRelease)) {
                    plugin.getLogger().log(Level.WARNING, "-----------------------------------");
                    plugin.getLogger().log(Level.WARNING, "There is an update available");
                    plugin.getLogger().log(Level.WARNING, "Current version: " + currentRelease);
                    plugin.getLogger().log(Level.WARNING, "Latest version: " + latestRelease);
                    plugin.getLogger().log(Level.WARNING, "-----------------------------------");
                }
            } catch (UpdateException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not get latest releases", e);
            }
        }, "KrafticsLib Update Checker").start();
    }

    public boolean isPreRelease() {
        return preRelease;
    }

    public static class GitHub {
        private Gson gson = new Gson();
        private String latestRelease;
        private String latestPreRelease;

        public void update() throws UpdateException {
            try {
                latestRelease = null;
                latestPreRelease = null;

                HttpURLConnection con = (HttpURLConnection) new URL("https://api.github.com/repos/KrafticsTeam/KrafticsLib/releases").openConnection();
                JsonArray array = gson.fromJson(new InputStreamReader(con.getInputStream()), JsonArray.class);

                for (int i = 0; i < array.size(); i++) {
                    JsonObject release = array.get(i).getAsJsonObject();
                    String tagName = release.get("tag_name").getAsString();
                    boolean preRelease = release.get("prerelease").getAsBoolean();

                    if (!preRelease && latestRelease == null) {
                        latestRelease = tagName;
                        if (latestPreRelease == null) {
                            latestPreRelease = tagName;
                        }
                    } else if (preRelease && latestPreRelease == null) {
                        latestPreRelease = tagName;
                    }
                }

                con.disconnect();
            } catch (Exception e) {
                throw new UpdateException("Could not get latest github release", e);
            }
        }

        @Nullable
        public String getLatestPreRelease() {
            return latestPreRelease;
        }

        @Nullable
        public String getLatestRelease() {
            return latestRelease;
        }
    }
}
