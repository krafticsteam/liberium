package com.kraftics.krafticslib.command.arguments;

import com.kraftics.krafticslib.command.Commands;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class BiomeArgumentType implements ArgumentType<Biome> {
    public static final Collection<String> EXAMPLES = Arrays.asList("plains", "dark_forest");

    private BiomeArgumentType() {
    }

    public static BiomeArgumentType biome() {
        return new BiomeArgumentType();
    }

    public static Biome getBiome(CommandContext<?> context, String name) {
        return context.getArgument(name, Biome.class);
    }

    @Override
    public Biome parse(StringReader reader) throws CommandSyntaxException {
        String biomeName = reader.readString();

        try {
            return Biome.valueOf(biomeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw Commands.NOT_FOUND.createWithContext(reader, "Biome", biomeName);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Biome biome : Biome.values()) {
            if (!biome.name().startsWith(builder.getRemaining().toUpperCase())) continue;
            builder.suggest(biome.name());
        }
        return CompletableFuture.completedFuture(builder.build());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
