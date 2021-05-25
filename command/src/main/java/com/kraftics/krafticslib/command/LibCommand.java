package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class LibCommand extends Command {
    private final CommandDispatcher dispatcher;
    private final RootCommandNode command;

    protected LibCommand(CommandDispatcher dispatcher, RootCommandNode command) {
        super(command.getName(), command.getDescription(), "/" + command.getName(), Arrays.asList(command.getAliases()));

        this.dispatcher = dispatcher;
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        StringJoiner joiner = new StringJoiner(" ");
        for (String arg : args) {
            joiner.add(arg);
        }

        try {
            ParseResult parse = dispatcher.parse(joiner.toString(), sender, command);
            dispatcher.execute(parse);
        } catch (CommandSyntaxException e) {
            sender.sendMessage(e.getMessage());
        }
        return true;
    }

    @Override
    @NotNull
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}
