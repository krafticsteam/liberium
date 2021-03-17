package com.kraftics.krafticslib.database;

import com.kraftics.krafticslib.database.cache.CacheDatabase;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.SQLException;

public interface Database {

    @Nullable
    Table table(String name);

    @NotNull
    Table createTable(String name, Table.Settings settings) throws DatabaseException;

    @NotNull
    default Table createTable(String name) throws DatabaseException {
        return createTable(name, Table.settings());
    }

    boolean hasTable(String name);

    void deleteTable(String name) throws DatabaseException;

    void updateTable(Table table) throws IllegalArgumentException, DatabaseException;

    static Database create(@NotNull Connection connection) {
        Validate.notNull(connection, "Connection cannot be null");

        return new CacheDatabase(connection);
    }

    static Database create(@NotNull File file) throws SQLException {
        return create(Connection.create(file));
    }

    static Database create(@NotNull String host, int port, @NotNull String database, @Nullable String username, @Nullable String password) throws SQLException {
        return create(Connection.create(host, port, database, username, password));
    }
}
