package com.kraftics.krafticslib.command;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends Command {
    private boolean executed = false;

    public TestCommand() {
        super("test", "Something");
    }

    @Override
    public void execute(CommandContext context) {
        executed = true;
    }

    @Override
    public List<String> tabComplete(TabContext context) {
        if (context.getIndex() == 0) {
            return Arrays.asList("test", "test2");
        }
        return null;
    }

    public boolean isExecuted() {
        return executed;
    }
}
