package com.kraftics.krafticslib.command;

import com.kraftics.krafticslib.command.exceptions.CommandSyntaxException;

import java.util.List;

public interface CommandNode {

    CommandExecutor getExecutor();

    List<CommandNode> getChildren();

    void addChild(CommandNode node);

    void parse(StringReader reader, CommandContextBuilder context) throws CommandSyntaxException;
}