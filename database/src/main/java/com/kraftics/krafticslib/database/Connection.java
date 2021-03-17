package com.kraftics.krafticslib.database;

import com.kraftics.krafticslib.database.utils.ConsumerSQL;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {
    private static boolean checked;

    private java.sql.Connection connection;
    private String url;
    private String username;
    private String password;

    /**
     * Connection constructor
     *
     * @param url The url
     * @param username The username
     * @param password The password
     */
    private Connection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Checks for the sqlite jdbc drivers
     *
     * @throws SQLException If the jdbc drivers were not found
     */
    public static void checkDrivers() throws SQLException {
        if (checked) return;

        try {
            Class.forName("org.sqlite.JDBC");
            checked = true;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Could not find sqlite jdbc driver", e);
        }
    }

    /**
     * Creates a new connection
     *
     * @param url The url connecting to
     * @param username The username for the database
     * @param password The password for the database
     * @return The created connection
     * @throws SQLException if database access error occurs
     */
    @Contract("_, _, _ -> new")
    @NotNull
    public static Connection create(@NotNull String url, @Nullable String username, @Nullable String password) throws SQLException {
        Validate.notNull(url, "URL cannot be null");

        checkDrivers();

        java.sql.Connection connection = DriverManager.getConnection(url, username, password);
        connection.close();

        return new Connection(url, username, password);
    }

    /**
     * Creates new sqlite connection
     *
     * @param file The file to connect to
     * @return The created connection
     * @throws SQLException if database access error occurs
     */
    @Contract("_ -> new")
    @NotNull
    public static Connection create(@NotNull File file) throws SQLException {
        Validate.notNull(file, "File cannot be null");

        return create("jdbc:sqlite:" + file, null, null);
    }

    /**
     * Creates a new mysql connection
     *
     * @param host The hostname of the mysql server
     * @param port The port of the mysql server
     * @param database The name of the mysql database
     * @param username The username for the mysql
     * @param password The password for the mysql
     * @return The created connection
     * @throws SQLException if database access error occurs
     */
    @Contract("_, _, _, _, _ -> new")
    @NotNull
    public static Connection create(@NotNull String host, int port, @NotNull String database, @Nullable String username, @Nullable String password) throws SQLException {
        return create("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }

    /**
     * Executes an update statement.
     * Remember to {@link #close()} the connection!
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException if database access error occurs
     */
    public int update(String sql, Object... args) throws SQLException {
        open();

        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }

        return ps.executeUpdate();
    }

    /**
     * Executes an update statement.
     * At the end it automatically closes
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws SQLException if database access error occurs
     */
    public void updateAndClose(String sql, Object... args) throws SQLException {
        try {
            update(sql, args);
        } finally {
            close();
        }
    }

    /**
     * Executes an update statement.
     * At the end it automatically closes
     *
     * @param run What do you want to do with the int result?
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws SQLException if database access error occurs
     */
    public void updateAndClose(ConsumerSQL<Integer> run, String sql, Object... args) throws SQLException {
        try {
            int i = update(sql, args);
            run.accept(i);
        } finally {
            close();
        }
    }

    /**
     * Executes a query statement.
     * Remember to {@link #close()} the connection!
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws SQLException if database access error occurs
     * @return The output from the query
     */
    @NotNull
    public ResultSet query(String sql, Object... args) throws SQLException {
        open();

        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }

        return ps.executeQuery();
    }

    /**
     * Executes a query statement.
     * At the end the connection automatically closes.
     *
     * @param run What do you want to do with the ResultSet?
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws SQLException if database access error occurs
     */
    public void queryAndClose(ConsumerSQL<ResultSet> run, String sql, Object... args) throws SQLException {
        try {
            ResultSet rs = query(sql, args);
            run.accept(rs);
        } finally {
            close();
        }
    }

    /**
     * Checks if the connection is closed.
     * This will use {@link java.sql.Connection#isClosed() isClosed} and {@link java.sql.Connection#isValid(int) isValid} methods
     *
     * @return if the connection is closed
     */
    public boolean isClosed() {
        try {
            return connection.isClosed() || connection.isValid(30);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes the connection. The connection will be set to null.
     *
     * @throws SQLException if database access error occurs
     */
    public void close() throws SQLException {
        if (connection == null || connection.isClosed()) return;
        connection.close();
        connection = null;
    }

    /**
     * Opens a new connection
     *
     * @throws SQLException if database access error occurs
     */
    public void open() throws SQLException {
        if (connection != null && !connection.isClosed()) return;
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Gets the sql connection.
     *
     * @return the connection, null if closed
     */
    @Nullable
    public java.sql.Connection connection() {
        return connection;
    }

    /**
     * Gets the sql url
     *
     * @return the url
     */
    @NotNull
    public String url() {
        return url;
    }

    /**
     * Sets the sql url
     *
     * @param url the url
     */
    public void url(@NotNull String url) {
        Validate.notNull(url);

        this.url = url;
    }

    /**
     * Gets the sql username
     *
     * @return the username, can be null
     */
    @Nullable
    public String username() {
        return username;
    }

    /**
     * Sets the sql username
     *
     * @param username the username, can be null
     */
    public void username(@Nullable String username) {
        this.username = username;
    }

    /**
     * Gets the sql password
     *
     * @return the password, can be null
     */
    @Nullable
    public String password() {
        return password;
    }

    /**
     * Sets the sql password
     *
     * @param password the password, can be null
     */
    public void password(@Nullable String password) {
        this.password = password;
    }
}
