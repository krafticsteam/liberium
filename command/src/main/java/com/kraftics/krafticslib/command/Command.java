package com.kraftics.krafticslib.command;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private final String name;
    private String description;
    private List<String> aliases;

    private String permission;
    private String permissionMessage;

    /**
     * Creates new command instance
     *
     * @param name the name of the command
     */
    public Command(@NotNull String name) {
        this(name, null, null, null, null);
    }

    /**
     * Creates new command instance
     *
     * @param name the name of the command
     * @param description the description of the command
     * @param aliases the aliases of the command
     */
    public Command(@NotNull String name, String description, String... aliases) {
        this(name, description, Arrays.asList(aliases), null, null);
    }

    /**
     * Creates new command instance
     *
     * @param name the name of the command
     * @param description the description of the command
     * @param aliases the aliases of the command
     */
    public Command(@NotNull String name, String description, List<String> aliases) {
        this(name, description, aliases, null, null);
    }

    /**
     * Creates new command instance
     *
     * @param name the name of the command
     * @param description the description of the command
     * @param aliases the aliases of the command
     * @param permission the permission needed to execute this command
     */
    public Command(@NotNull String name, String description, List<String> aliases, String permission) {
        this(name, description, aliases, permission, null);
    }

    /**
     * Creates new command instance
     *
     * @param name the name of the command
     * @param description the description of the command
     * @param aliases the aliases of the command
     * @param permission the permission needed to execute this command
     * @param permissionMessage the permission message sended when not enough permissions
     */
    public Command(@NotNull String name, String description, List<String> aliases, String permission, String permissionMessage) {
        Validate.notNull(name, "Name cannot be null");

        this.name = name;
        this.description = description == null ? "" : description;
        this.aliases = aliases == null ? new ArrayList<>() : aliases;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
    }

    /**
     * Executes the command
     *
     * @param context Context of the command
     */
    public abstract void execute(CommandContext context);

    /**
     * Executed on tab completion for this command,
     * returning a list of options the player can tab through.
     *
     * @param context Context of the tab completion
     * @return a list of tab-completions for the specified arguments. List may be immutable.
     */
    @Nullable
    public List<String> tabComplete(TabContext context) {
        return null;
    }

    /**
     * Gets the name of this command
     *
     * @return the name
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Gets the description of this command
     *
     * @return the description
     */
    @NotNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this command
     *
     * @param description the description
     * @return this command
     */
    @NotNull
    public Command setDescription(@NotNull String description) {
        Validate.notNull(description, "Description cannot be null");

        this.description = description;
        return this;
    }

    /**
     * Gets the aliases of this command
     *
     * @return the aliases
     */
    @NotNull
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Adds alias to this command
     *
     * @param s the alias
     * @return this command
     */
    @NotNull
    public Command addAlias(@NotNull String s) {
        Validate.notNull(s, "Alias cannot be null");

        aliases.add(s);
        return this;
    }

    /**
     * Removes alias to this command
     *
     * @param s the alias
     * @return this command
     */
    @NotNull
    public Command removeAlias(@NotNull String s) {
        Validate.notNull(s, "Alias cannot be null");

        aliases.remove(s);
        return this;
    }

    /**
     * Sets the alises of this command
     *
     * @param aliases the aliases
     * @return this command
     */
    @NotNull
    public Command setAliases(@NotNull List<String> aliases) {
        Validate.notNull(aliases, "Aliases cannot be null");

        this.aliases = aliases;
        return this;
    }

    /**
     * Gets the permission needed to execute this command
     *
     * @return the permission
     */
    @Nullable
    public String getPermission() {
        return permission;
    }

    /**
     * Sets the permission needed to execute this command
     *
     * @param permission the permission
     * @return this command
     */
    @NotNull
    public Command setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Gets the permission message sent when user does not have {@link #getPermission()}
     *
     * @return the permission message
     */
    @Nullable
    public String getPermissionMessage() {
        return permissionMessage;
    }

    /**
     * Sets the permission message sent when user does not have {@link #getPermission()}
     *
     * @param permissionMessage the permission message
     * @return this command
     */
    @NotNull
    public Command setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }
}