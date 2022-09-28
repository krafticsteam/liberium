package com.kraftics.liberium.nms.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandListenerWrapper;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;

public class WrappedCommandContext extends CommandContext<CommandSender> {
    protected final CommandContext<CommandListenerWrapper> original;

    protected WrappedCommandContext(CommandContext<CommandListenerWrapper> original, Command<CommandSender> command) {
        super(original.getSource().getBukkitSender(), original.getInput(), new HashMap<>(), command, null, new ArrayList<>(), original.getRange(), from(original.getChild()), null, original.isForked());
        this.original = original;
    }

    public static WrappedCommandContext from(CommandContext<CommandListenerWrapper> ctx) {
        return from(ctx, null);
    }

    public static WrappedCommandContext from(CommandContext<CommandListenerWrapper> ctx, Command<CommandSender> reference) {
        if (ctx == null) return null;
        return new WrappedCommandContext(ctx, reference);
    }

    @Override
    public <V> V getArgument(String name, Class<V> clazz) {
        return original.getArgument(name, clazz);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WrappedCommandContext && super.equals(o) && original.equals(((WrappedCommandContext) o).original);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + original.hashCode();
        return result;
    }

    @Override
    public CommandNode<CommandSender> getRootNode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RedirectModifier<CommandSender> getRedirectModifier() {
        throw new UnsupportedOperationException();
    }
}
