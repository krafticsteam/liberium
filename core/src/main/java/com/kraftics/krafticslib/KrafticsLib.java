package com.kraftics.krafticslib;

import com.kraftics.krafticslib.bukkit.BukkitKrafticsLibAPI;
import com.kraftics.krafticslib.command.Commands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to create or manage apis
 */
public class KrafticsLib {
    private static Set<KrafticsLibAPI> apis = new HashSet<>();

    /**
     * Creates new {@link KrafticsLibAPI} for bukkit
     *
     * @param plugin The plugin using this api
     * @return The api
     */
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

    /**
     * Executes the command passing all the apis
     *
     * @param message The full command message without the {@code '/'}
     * @param sender Sender who is executing the command
     * @return true if the command was found, false if not
     */
    public static boolean executeCommand(String message, CommandSender sender) {
        for (KrafticsLibAPI api : apis) {
            try {
                CommandDispatcher<CommandSender> dispatcher = api.getCommandDispatcher();
                ParseResults<CommandSender> parse = api.getCommandDispatcher().parse(message, sender);
                if (parse.getReader().canRead() && parse.getContext().getRange().isEmpty()) continue;
                int result = dispatcher.execute(parse);

                if (result == Commands.NO_PERMISSIONS) {
                    sender.sendMessage(Commands.NO_PERMISSIONS_EXCEPTION.create().getMessage());
                }

                return true;
            } catch (Exception e) {
                sender.sendMessage(e.getMessage());
                return true;
            }
        }

        return false;
    }

    /**
     * Returns suggestions used by tab complete
     *
     * @param message The full command message without the {@code '/'}
     * @param sender Sender who is executing the command
     * @return a future that will eventually resolve into a {@link Suggestions} object
     */
    public static CompletableFuture<Suggestions> getSuggestions(String message, CommandSender sender) {
        for (KrafticsLibAPI api : apis) {
            try {
                CommandDispatcher<CommandSender> dispatcher = api.getCommandDispatcher();
                ParseResults<CommandSender> parse = api.getCommandDispatcher().parse(message, sender);
                if (parse.getReader().canRead() && parse.getContext().getRange().isEmpty()) continue;
                return dispatcher.getCompletionSuggestions(parse);
            } catch (Exception e) {
                sender.sendMessage(e.getMessage());
            }
        }

        return Suggestions.empty();
    }
}
