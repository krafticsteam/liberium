package com.kraftics.krafticslib.command;

import org.bukkit.command.CommandMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    public void test() {
        CommandMap commandMap = new FakeCommandMap();
        CommandDispatcher dispatcher = new CommandDispatcher("test", commandMap);
        TestCommand testCommand = new TestCommand();

        dispatcher.register(testCommand);

        commandMap.dispatch(new FakeCommandSender(), "test");
        assertTrue(testCommand.isExecuted());
        List<String> list = commandMap.tabComplete(new FakeCommandSender(), "test ");
        assertEquals(Arrays.asList("test", "test2"), list);
        list = commandMap.tabComplete(new FakeCommandSender(), "test test ");
        assertEquals(new ArrayList<>(), list);
        list = commandMap.tabComplete(new FakeCommandSender(), "test");
        assertEquals(new ArrayList<>(), list);
    }
}

