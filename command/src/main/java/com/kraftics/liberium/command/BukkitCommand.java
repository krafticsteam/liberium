package com.kraftics.liberium.command;

import com.kraftics.liberium.command.exceptions.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class BukkitCommand extends Command {
    private final CommandDispatcher dispatcher;
    private final RootCommandNode command;

    protected BukkitCommand(CommandDispatcher dispatcher, RootCommandNode command) {
        super(command.getName(), command.getDescription(), "/" + command.getName(), Arrays.asList(command.getAliases()));

        this.dispatcher = dispatcher;
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        String input = null;
        if (args.length > 0) {
            StringJoiner joiner = new StringJoiner(" ");
            for (String arg : args) {
                joiner.add(arg);
            }
            input = joiner.toString();
        }

        try {
            ParseResult parse = dispatcher.parse(input, sender, command);
            dispatcher.execute(parse);
        } catch (CommandSyntaxException e) {
            sender.sendMessage(e.getColoredMessage());
        }
        return true;
    }

    @Override
    @NotNull
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        String input = null;
        if (args.length > 0) {
            StringJoiner joiner = new StringJoiner(" ");
            for (String arg : args) {
                joiner.add(arg);
            }
            input = joiner.toString();
        }

        ParseResult parse = dispatcher.parse(input, sender, command);
        return dispatcher.tabComplete(parse);
    }
}
