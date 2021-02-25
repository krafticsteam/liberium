package com.kraftics.krafticslib.command.arguments;

import com.kraftics.krafticslib.command.Commands;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class WorldArgumentType implements ArgumentType<World> {
    public static final Collection<String> EXAMPLES = Arrays.asList("world", "world_nether", "test_world");

    private WorldArgumentType() {

    }

    public static WorldArgumentType world() {
        return new WorldArgumentType();
    }

    public static World getWorld(CommandContext<?> context, String name) {
        return context.getArgument(name, World.class);
    }

    @Override
    public World parse(StringReader reader) throws CommandSyntaxException {
        String worldName = reader.readString();
        World world = Bukkit.getWorld(worldName);
        if (world == null) throw Commands.NOT_FOUND.createWithContext(reader, "World", worldName);
        return world;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().startsWith(builder.getRemaining())) continue;
            builder.suggest(world.getName());
        }
        return CompletableFuture.completedFuture(builder.build());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
