package com.kraftics.liberium.command.argument;

import com.kraftics.liberium.command.StringReader;
import com.kraftics.liberium.command.exceptions.CommandSyntaxException;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlockArgument extends Argument<Material> {
    private static final List<String> TAB_COMPLETE = new ArrayList<>();

    static {
        for (Material material : Material.values()) {
            if (material.isBlock()) {
                TAB_COMPLETE.add(material.name().toLowerCase(Locale.ROOT));
            }
        }
    }

    public BlockArgument(String name) {
        super(name);
    }

    @Override
    public Material parse(StringReader reader) throws CommandSyntaxException {
        Material material = Material.getMaterial(reader.readUnquotedString().toUpperCase(Locale.ROOT));
        if (material == null || !material.isBlock()) throw CommandSyntaxException.BuiltIn.INVALID_BLOCK.build(reader);
        return material;
    }

    @Override
    public List<String> tabComplete(StringReader reader) throws CommandSyntaxException {
        return Argument.contextOnly(TAB_COMPLETE, reader);
    }
}
