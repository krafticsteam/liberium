package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.utils.ConsumerSQL;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * SQL connection
 *
 * @author Panda885
 */
public class ConnectionSQL {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private List<AutoCloseable> closing = new ArrayList<>();

    /**
     * Constructs connection by url, username and password
     *
     * @param url Url to the database
     * @param username Username for the database
     * @param password Password for the database
     * @throws DatabaseException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull String url, @Nullable String username, @Nullable String password) throws DatabaseException {
        Validate.notNull(url, "URL cannot be null");

        this.url = url;
        this.username = username;
        this.password = password;

        connect();
        close();
    }

    /**
     * Constructs mysql connection by host, port, databaseName, username and password
     *
     * @param host Host of the database
     * @param port Port of the database
     * @param databaseName Name of the database
     * @param username Username for the database
     * @param password Password for the database
     * @throws DatabaseException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull String host, int port, @Nonnull String databaseName, @Nullable String username, @Nullable String password) throws DatabaseException {
        this("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password);
    }

    /**
     * Constructs sqlite connection by file
     *
     * @param file The file.
     * @throws DatabaseException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull File file) throws DatabaseException {
        this("jdbc:sqlite:" + file.getPath(), null, null);
    }

    /**
     * Prepares an update statement and executes it.
     * Remember to {@link #close()} the connection!
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws DatabaseException If something went wrong
     */
    public void update(String sql, Object... args) throws DatabaseException {
        connect();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            closing.add(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Prepares an update statement and executes it.
     * At the end it automatically closes
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws DatabaseException If something went wrong
     */
    public void updateAndClose(String sql, Object... args) throws DatabaseException {
        try {
            update(sql, args);
        } finally {
            close();
        }
    }

    /**
     * Prepares a query statement and executes it.
     * Remember to {@link #close()} the connection!
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws DatabaseException If something went wrong
     */
    @Nonnull
    public ResultSet query(String sql, Object... args) throws DatabaseException {
        connect();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            closing.add(ps);
            ResultSet rs = ps.executeQuery();
            closing.add(rs);

            return rs;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Prepares a query statement and executes it.
     * At the end it automatically closes
     *
     * @param sql The SQL statement
     * @param args Arguments replacing the ? character
     * @throws DatabaseException If something went wrong
     */
    public void queryAndClose(ConsumerSQL<ResultSet> run, String sql, Object... args) throws DatabaseException {
        try {
            ResultSet rs = query(sql, args);
            run.accept(rs);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }
    }

    /**
     * Gets the connection (could be closed)
     * @return The connection
     */
    @Nonnull
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the url (doesn't need to reconnect)
     * @param url The url
     */
    public void setUrl(@Nonnull String url) {
        Validate.notNull(url, "URL cannot be null");

        this.url = url;
    }

    /**
     * Sets the password (doesn't need to reconnect)
     * @param password The password
     */
    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    /**
     * Sets the username (doesn't need to reconnect)
     * @param username The username
     */
    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    /**
     * Gets the url
     * @return The url
     */
    @Nonnull
    public String getURL() {
        return url;
    }

    /**
     * Gets the username
     * @return The username
     */
    @Nullable
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password
     * @return The password
     */
    @Nullable
    public String getPassword() {
        return password;
    }

    /**
     * Closes the connection and other objects that
     * are needed to close
     *
     * @throws DatabaseException If could not close the connection
     */
    public void close() throws DatabaseException {
        try {
            if (connection == null || connection.isClosed()) return;

            for (AutoCloseable c : closing) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            closing.clear();

            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Creates the connection
     *
     * @throws DatabaseException If could not create the connection
     */
    public void connect() throws DatabaseException {
        try {
            if (connection != null && !connection.isClosed()) return;

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
