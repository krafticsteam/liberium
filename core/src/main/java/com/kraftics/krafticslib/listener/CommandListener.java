package com.kraftics.krafticslib.listener;

import com.kraftics.krafticslib.KrafticsLib;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        System.out.println("Command: " + event.getMessage().substring(1));
        if (KrafticsLib.executeCommand(event.getMessage().substring(1), event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerCommand(ServerCommandEvent event) {
        System.out.println("Command: " + event.getCommand());
        if (KrafticsLib.executeCommand(event.getCommand(), event.getSender())) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTabComplete(TabCompleteEvent event) {
        System.out.println("TAB: " + event.getBuffer());
        try {
            CompletableFuture<Suggestions> future = KrafticsLib.getSuggestions(event.getBuffer(), event.getSender());
            Suggestions suggestions = future.get();
            if (suggestions.isEmpty()) return;

            List<String> list = new ArrayList<>();
            for (Suggestion suggestion : suggestions.getList()) {
                list.add(suggestion.getText());
            }
            event.setCompletions(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
