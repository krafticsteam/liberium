package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

/**
 * Class representing a command node
 */
public interface CommandNode {

    CommandExecutor getExecutor();

    List<CommandNode> getChildren();

    void addChild(CommandNode node);

    void parse(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException;

    List<String> tabComplete(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException;
}
