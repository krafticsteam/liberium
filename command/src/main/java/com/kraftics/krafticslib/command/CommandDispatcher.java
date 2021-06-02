package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class is used to register commands.
 * <p>
 * This will also register the command to Bukkit's CommandMap.
 * You can get commands registered by this class using {@link #getCommands()}.
 * </p>
 */
public class CommandDispatcher {
    private final RootNode root = new RootNode();
    private final CommandMap map;
    private final String prefix;

    /**
     * Creates a new command dispatcher
     *
     * @param prefix The prefix to use for registering commands - {@code /prefix:command}
     * @param map Bukkit's command map to register the commands
     */
    public CommandDispatcher(@NotNull String prefix, @NotNull CommandMap map) {
        Validate.notNull(prefix, "Prefix cannot be null");
        Validate.notNull(map, "Map cannot be null");

        this.map = map;
        this.prefix = prefix;
    }

    /**
     * Parses an input to a parse result
     *
     * @param sender a sender who executed this command
     * @param input a command input
     * @return a parse result
     */
    public ParseResult parse(@NotNull CommandSender sender, @NotNull String input) {
        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(sender, "Sender cannot be null");

        return parse(sender, new StringReader(input));
    }

    /**
     * Parses an input to a parse result
     *
     * @param sender a sender who executed this command
     * @param reader a command input reader
     * @return a parse result
     */
    public ParseResult parse(@NotNull CommandSender sender, @NotNull StringReader reader) {
        Validate.notNull(reader, "Reader cannot be null");
        Validate.notNull(sender, "Sender cannot be null");

        return parseNodes(reader, root, new CommandContextBuilder(sender, reader.getString()));
    }

    protected ParseResult parse(String input, @NotNull CommandSender sender, @NotNull RootCommandNode child) {
        Validate.notNull(child, "Command cannot be null");
        Validate.notNull(sender, "Sender cannot be null");

        boolean empty = input == null;
        String s = empty ? child.getName() : child.getName() + " " + input;
        StringReader reader = new StringReader(s);
        CommandContextBuilder context = new CommandContextBuilder(sender, s);
        context.withCommand(child);

        try {
            child.parse(reader, context);

            if (reader.canRead() && reader.peek() != ' ') {
                throw CommandSyntaxException.BuiltIn.EXPECTED_ARGUMENT_SEPERATOR.build(reader);
            }
        } catch (CommandSyntaxException e) {
            return new ParseResult(reader, context, Collections.singletonMap(child, e));
        }

        if (empty) {
            return new ParseResult(reader, context, new HashMap<>());
        }

        context.withParent(child);
        reader.skip();
        return parseNodes(reader, child, context);
    }

    private static ParseResult parseNodes(StringReader reader, CommandNode node, CommandContextBuilder context) {
        Map<CommandNode, CommandSyntaxException> exceptions = new HashMap<>();
        List<ParseResult> potentials = new ArrayList<>();
        if (!node.getChildren().isEmpty()) context.withParent(node);

        for (CommandNode child : node.getChildren()) {
            StringReader readerCopy = new StringReader(reader);
            CommandContextBuilder contextCopy = context.copy();

            try {
                child.parse(readerCopy, contextCopy);

                if (readerCopy.canRead() && readerCopy.peek() != ' ') {
                    throw CommandSyntaxException.BuiltIn.EXPECTED_ARGUMENT_SEPERATOR.build(readerCopy);
                }
            } catch (CommandSyntaxException e) {
                exceptions.put(child, e);
                continue;
            }

            contextCopy.withCommand(child);

            ParseResult parse;
            if (readerCopy.canRead()) {
                readerCopy.skip();
                parse = parseNodes(readerCopy, child, contextCopy);
            } else {
                parse = new ParseResult(readerCopy, contextCopy, new HashMap<>());
            }
            potentials.add(parse);
        }

        if (!potentials.isEmpty()) {
            for (ParseResult parse : potentials) {
                if (parse.getExceptions().isEmpty()) {
                    return parse;
                }
            }
            return potentials.get(0);
        }

        return new ParseResult(reader, context, exceptions);
    }

    /**
     * Registers a command to the dispatcher,
     * this will also register the command to entered command map
     *
     * @param name the name of the command
     * @param consumer the command to register
     */
    public void register(@NotNull String name, @NotNull Consumer<CommandBuilder> consumer) {
        register(prefix, name, consumer);
    }

    /**
     * Register a command to the dispatcher,
     * this will also register the command to entered command map
     *
     * @param prefix the prefix of the command - {@code /prefix:command}
     * @param name the name of the command
     * @param consumer the command to register
     */
    public void register(@NotNull String prefix, @NotNull String name, @NotNull Consumer<CommandBuilder> consumer) {
        CommandBuilder builder = new CommandBuilder(name);
        consumer.accept(builder);
        RootCommandNode node = builder.build();

        this.root.commands.add(node);
        map.register(prefix, new LibCommand(this, node));
    }

    /**
     * Executes a command
     *
     * @param sender a sender who executed this command
     * @param input a command input
     * @throws CommandSyntaxException if the command failed to executed
     */
    public void execute(@NotNull CommandSender sender, @NotNull String input) throws CommandSyntaxException {
        execute(parse(sender, input));
    }

    /**
     * Executes a command
     *
     * @param sender a sender who executed this command
     * @param reader a command input reader
     * @throws CommandSyntaxException if the command failed to executed
     */
    public void execute(@NotNull CommandSender sender, @NotNull StringReader reader) throws CommandSyntaxException {
        execute(parse(sender, reader));
    }

    /**
     * Executes a command
     *
     * @param parse a successful parse result
     * @throws CommandSyntaxException if the command failed to executed
     */
    public void execute(@NotNull ParseResult parse) throws CommandSyntaxException {
        if (!parse.getExceptions().isEmpty()) {
            if (parse.getExceptions().size() == 1) {
                throw parse.getExceptions().values().iterator().next();
            } else if (parse.getContext().getCommand() == null) {
                throw CommandSyntaxException.BuiltIn.INVALID_COMMAND.build(parse.getReader());
            } else {
                throw CommandSyntaxException.BuiltIn.INVALID_ARGUMENT.build(parse.getReader());
            }
        }

        try {
            CommandContext context = parse.getContext().build();
            if (context.getCommand().getExecutor() == null) throw CommandSyntaxException.BuiltIn.EXPECTED_ARGUMENT.build(parse.getReader());
            context.getCommand().getExecutor().execute(context);
        } catch (Exception e) {
            if (e instanceof CommandSyntaxException) {
                throw e;
            }
            e.printStackTrace();
            throw CommandSyntaxException.BuiltIn.UNEXPECTED.build(e);
        }
    }

    /**
     * Just tab completes bruh
     *
     * @param parse The parse result
     * @return List of tab completions
     */
    public List<String> tabComplete(ParseResult parse) {
        if (parse.getReader().getString().split(" ", -1).length != parse.getReader().getRange(0, parse.getReader().getCursor()).split(" ", -1).length) {
            return new ArrayList<>();
        }

        CommandNode parent = parse.getContext().getParent();
        if (parent == null) return new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (CommandNode child : parent.getChildren()) {
            try {
                List<String> current = child.tabComplete(new StringReader(parse.getReader()), parse.getContext());
                if (current == null || current.isEmpty()) continue;
                list.addAll(current);
            } catch (Exception ignored) {

            }
        }
        return list;
    }

    /**
     * Gets a command by name
     *
     * @param name the name
     * @return the command, null if not found
     */
    @Nullable
    public RootCommandNode getCommand(String name) {
        for (RootCommandNode command : root.commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public List<RootCommandNode> getCommands() {
        return root.commands;
    }

    /**
     * Gets prefix of this command dispatcher - {@code /prefix:command}
     *
     * @return the prefix
     */
    @NotNull
    public String getPrefix() {
        return prefix;
    }

    public CommandMap getMap() {
        return map;
    }

    private static class RootNode implements CommandNode {
        private final List<RootCommandNode> commands = new ArrayList<>();

        @Override
        public CommandExecutor getExecutor() {
            return null;
        }

        @Override
        public List<CommandNode> getChildren() {
            return new ArrayList<>(commands);
        }

        @Override
        public void addChild(CommandNode node) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void parse(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> tabComplete(StringReader reader, CommandContextBuilder context) {
            throw new UnsupportedOperationException();
        }
    }
}
