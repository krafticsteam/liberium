package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {
    private CommandDispatcher dispatcher;
    private TestCommand testCommand;
    private CommandMap commandMap;

    @Test
    public void test() {
        commandMap = new FakeCommandMap();
        dispatcher = new CommandDispatcher(commandMap);
        testCommand = new TestCommand();

        dispatcher.register(testCommand);

        commandMap.dispatch(null, "test");
        Assertions.assertTrue(testCommand.isExecuted());
        List<String> list = commandMap.tabComplete(null, "test ");
        Assertions.assertEquals(Arrays.asList("test", "test2"), list);
        list = commandMap.tabComplete(null, "test test ");
        Assertions.assertEquals(new ArrayList<>(), list);
        list = commandMap.tabComplete(null, "test");
        Assertions.assertEquals(new ArrayList<>(), list);
    }
}

