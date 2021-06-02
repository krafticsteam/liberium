package com.kraftics.krafticslib.command.argument;

import com.kraftics.krafticslib.command.StringReader;
import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemArgument implements Argument<Material> {
    private static final List<String> TAB_COMPLETE = new ArrayList<>();
    private final String name;

    static {
        for (Material material : Material.values()) {
            if (material.isItem()) {
                TAB_COMPLETE.add(material.name().toLowerCase(Locale.ROOT));
            }
        }
    }

    public ItemArgument(String name) {
        this.name = name;
    }

    @Override
    public Material parse(StringReader reader) throws CommandSyntaxException {
        Material material = Material.getMaterial(reader.readUnquotedString().toUpperCase(Locale.ROOT));
        if (material == null || !material.isItem()) throw CommandSyntaxException.BuiltIn.INVALID_ITEM.build(reader);
        return material;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return Argument.contextOnly(TAB_COMPLETE, reader);
    }

    @Override
    public String getName() {
        return name;
    }
}
