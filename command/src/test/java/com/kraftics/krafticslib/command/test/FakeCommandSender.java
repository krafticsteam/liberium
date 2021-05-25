package com.kraftics.krafticslib.command.test;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

public class FakeCommandSender implements CommandSender {
    private boolean success = false;
    private boolean op = false;

    @Override
    public void sendMessage(@NotNull String message) {
        System.out.println(message);
    }

    @Override
    public void sendMessage(@NotNull String[] messages) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String msg : messages) {
            joiner.add(msg);
        }
        sendMessage(joiner.toString());
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        sendMessage(message);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String[] messages) {
        sendMessage(messages);
    }

    @NotNull
    @Override
    public Server getServer() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "TestPlayer123";
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return true;
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return true;
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return null;
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    @Override
    public boolean isOp() {
        return op;
    }

    @Override
    public void setOp(boolean value) {
        this.op = value;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
