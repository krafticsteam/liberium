package com.kraftics.liberium.command.test;

import com.kraftics.liberium.command.CommandDispatcher;
import com.kraftics.liberium.command.argument.StringArgument;
import org.bukkit.command.CommandMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTest {
    private final FakeCommandSender sender = new FakeCommandSender();
    private final CommandMap map = new FakeCommandMap();
    private final CommandDispatcher dispatcher = new CommandDispatcher("test", map);

    @BeforeEach
    public void before() {
        sender.setSuccess(false);
    }

    @Test
    public void first() {
        dispatcher.register("first", cmd -> {
            cmd.setDescription("Some description");
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey! This is a test command.");
                ((FakeCommandSender) ctx.getSender()).setSuccess(true);
            });
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey! You should not see this!");
            }, new StringArgument("egg"));
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey! You should not see this! But not.");
            }, new StringArgument("egg"), new StringArgument("eggo"));
        });

        map.dispatch(sender, "first");

        Assertions.assertTrue(sender.getSuccess());
    }

    @Test
    public void second() {
        dispatcher.register("second", cmd -> {
            cmd.setDescription("Some second description");
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey second! You should not see this!");
            });
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey! This is a second test command.");
                ((FakeCommandSender) ctx.getSender()).setSuccess(true);
            }, new StringArgument("egg"));
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey second! You should not see this! But not.");
            }, new StringArgument("egg"), new StringArgument("eggo"));
        });

        map.dispatch(sender, "second lol");

        Assertions.assertTrue(sender.getSuccess());
    }

    @Test
    public void third() {
        dispatcher.register("third", cmd -> {
            cmd.setDescription("Some third description");
            cmd.execute(ctx -> {
                ctx.getSender().sendMessage("Hey third! You should not see this!");
                ((FakeCommandSender) ctx.getSender()).setSuccess(true);
            }, new StringArgument("egg"));
        });

        map.dispatch(sender, "third");

        Assertions.assertFalse(sender.getSuccess());
    }

}
