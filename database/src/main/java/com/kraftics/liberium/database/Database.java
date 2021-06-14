package com.kraftics.liberium.database;

import com.kraftics.liberium.database.cache.CacheDatabase;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.SQLException;

/**
 * This class represents a sql database
 */
public interface Database {

    /**
     * Gets a table by name.
     * <p>
     * This will normally query data from the database and create a {@link Table} object
     * </p>
     *
     * @param name The name of the table
     * @return The table
     */
    @Nullable
    Table getTable(String name);

    /**
     * Creates a table with name and {@link Table.Settings}
     *
     * @param name The name of the table
     * @param settings The settings of the table
     * @return The created table
     * @throws DatabaseException If the table could not be created
     */
    @NotNull
    Table createTable(String name, Table.Settings settings) throws DatabaseException;

    /**
     * Creates a table with name and default settings
     *
     * @param name The name of the table
     * @return The created table
     * @throws DatabaseException If the table could not be created
     */
    @NotNull
    default Table createTable(String name) throws DatabaseException {
        return createTable(name, Table.settings());
    }

    /**
     * Checks if this database contains a table with name
     *
     * @param name The name of a table
     * @return If the table exists
     */
    boolean hasTable(String name);

    /**
     * Deletes a table from this database
     *
     * @param name The name of the table
     * @throws DatabaseException If the table could not be deleted
     */
    void deleteTable(String name) throws DatabaseException;

    /**
     * Updates a table to the database (pushes table data)
     *
     * @param table The table to update
     * @throws IllegalArgumentException If the specified table is not a valid type (class)
     * @throws DatabaseException If the table could not be updated
     */
    void updateTable(Table table) throws IllegalArgumentException, DatabaseException;

    /**
     * Creates a cached database with {@link Connection}
     *
     * @param connection The connection to database
     * @return The created database
     */
    static Database create(@NotNull Connection connection) {
        Validate.notNull(connection, "Connection cannot be null");

        return new CacheDatabase(connection);
    }

    /**
     * Creates a sqlite database
     *
     * @param file The database file
     * @return The created database
     * @throws SQLException If the file is invalid
     */
    static Database create(@NotNull File file) throws SQLException {
        return create(Connection.create(file));
    }

    /**
     * Creates a mysql database
     *
     * @param host The hostname of the mysql server
     * @param port The port of the mysql server
     * @param database The name of the mysql database
     * @param username The username for the mysql
     * @param password The password for the mysql
     * @return The created connection
     * @throws SQLException if database access error occurs
     */
    static Database create(@NotNull String host, int port, @NotNull String database, @Nullable String username, @Nullable String password) throws SQLException {
        return create(Connection.create(host, port, database, username, password));
    }
}
