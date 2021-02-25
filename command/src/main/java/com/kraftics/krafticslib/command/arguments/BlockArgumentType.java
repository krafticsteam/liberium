package com.kraftics.krafticslib.command.arguments;

import com.kraftics.krafticslib.command.Commands;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class BlockArgumentType implements ArgumentType<Material> {
    public static final Collection<String> EXAMPLES = Arrays.asList("stone", "diamond_ore", "oak_wood");

    private BlockArgumentType() {

    }

    public static BlockArgumentType block() {
        return new BlockArgumentType();
    }

    public static Material getBlock(CommandContext<?> context, String name) {
        return context.getArgument(name, Material.class);
    }

    @Override
    public Material parse(StringReader reader) throws CommandSyntaxException {
        String materialName = reader.readString();
        Material material = Material.getMaterial(materialName);
        if (material == null || !material.isBlock()) throw Commands.NOT_FOUND.createWithContext(reader, "Block", materialName);
        return material;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Material material : Material.values()) {
            if (!material.isBlock() || !material.name().startsWith(builder.getRemaining().toUpperCase())) continue;
            builder.suggest(material.name());
        }
        return CompletableFuture.completedFuture(builder.build());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
