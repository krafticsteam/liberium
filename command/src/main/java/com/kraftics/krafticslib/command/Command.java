package com.kraftics.krafticslib.command;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private final String name;
    private String description;
    private List<String> aliases;

    private String permission;
    private String permissionMessage;

    public Command(String name) {
        this(name, null, null, null, null);
    }

    public Command(String name, String description, String... aliases) {
        this(name, description, Arrays.asList(aliases), null, null);
    }

    public Command(String name, String description, List<String> aliases) {
        this(name, description, aliases, null, null);
    }

    public Command(String name, String description, List<String> aliases, String permission) {
        this(name, description, aliases, permission, null);
    }

    public Command(String name, String description, List<String> aliases, String permission, String permissionMessage) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
    }

    public abstract void execute(CommandContext context);

    public List<String> tabComplete(TabContext context) {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Command setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public Command addAlias(String s) {
        aliases.add(s);
        return this;
    }

    public Command removeAlias(String s) {
        aliases.remove(s);
        return this;
    }

    public Command setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Command setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public Command setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }
}