package com.kraftics.liberium.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class BukkitCommand extends Command {
    private final CommandDispatcher<CommandSender> dispatcher;
    private final com.kraftics.liberium.command.Command command;

    protected BukkitCommand(CommandDispatcher<CommandSender> dispatcher, com.kraftics.liberium.command.Command command) {
        super(command.name(), command.description(), "/" + command.name(), Arrays.asList(command.aliases()));

        this.dispatcher = dispatcher;
        this.command = command;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        String input = getInput(args);
        ParseResults<CommandSender> parseResults = dispatcher.parse(input, sender);

        try {
            dispatcher.execute(parseResults);
        } catch (CommandSyntaxException e) {
            printException(sender, e);
        }

        return true;
    }

    @Override
    @NotNull
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        String input = getInput(args);
        ParseResults<CommandSender> parseResults = dispatcher.parse(input, sender);
        List<String> result = new ArrayList<>();

        try {
            Suggestions suggestions = dispatcher.getCompletionSuggestions(parseResults).get();

            for (Suggestion suggestion : suggestions.getList()) {
                result.add(suggestion.getText());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getInput(String[] args) {
        String input = command.name();
        if (args.length > 0) {
            StringJoiner joiner = new StringJoiner(" ");
            for (String arg : args) {
                joiner.add(arg);
            }
            input = input + " " +  joiner;
        }
        return input;
    }

    public void printException(CommandSender sender, CommandSyntaxException e) {
        String message = "\u00a7c" + e.getRawMessage().getString();
        String context = null;

        if (e.getInput() != null && e.getCursor() >= 0) {
            final StringBuilder builder = new StringBuilder();
            final int cursor = Math.min(e.getInput().length(), e.getCursor());

            builder.append("\u00a77");

            if (cursor > CommandSyntaxException.CONTEXT_AMOUNT) {
                builder.append("...");
            }

            builder.append(e.getInput(), Math.max(0, cursor - CommandSyntaxException.CONTEXT_AMOUNT), cursor);
            builder.append("\u00a7c<--[HERE]");

            context = builder.toString();
        }

        sender.sendMessage(message + (context == null ? "" : "\n" + context));
    }
}
