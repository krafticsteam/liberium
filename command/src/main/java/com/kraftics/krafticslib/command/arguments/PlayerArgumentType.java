package com.kraftics.krafticslib.command.arguments;

import com.kraftics.krafticslib.command.Commands;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerArgumentType implements ArgumentType<Player> {
    public static final Collection<String> EXAMPLES = Arrays.asList("Panda885", "Dinnerbone", "CookieCake287");

    private PlayerArgumentType() {

    }

    public static PlayerArgumentType player() {
        return new PlayerArgumentType();
    }

    public static Player getPlayer(CommandContext<?> context, String name) {
        return context.getArgument(name, Player.class);
    }

    @Override
    public Player parse(StringReader reader) throws CommandSyntaxException {
        String playerName = reader.readString();
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) throw Commands.NOT_FOUND.createWithContext(reader, "Player", playerName);
        return player;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getName().startsWith(builder.getRemaining())) continue;
            builder.suggest(player.getName());
        }
        return CompletableFuture.completedFuture(builder.build());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
