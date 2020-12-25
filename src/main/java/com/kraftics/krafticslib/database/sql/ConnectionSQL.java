package com.kraftics.krafticslib.database.sql;

//import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.sql.*;
import java.util.List;

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

    /**
     * Constructs mysql database by url, username and password
     *
     * @param url Url to the database
     * @param username Username for the database
     * @param password Password for the database
     * @throws SQLException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull String url, @Nullable String username, @Nullable String password) throws SQLException {
//        Validate.notNull(url, "URL cannot be null");

        this.url = url;
        this.username = username;
        this.password = password;

        connect();
        close();
    }

    /**
     * Constructs mysql database by host, port, databaseName, username and password
     *
     * @param host Host of the database
     * @param port Port of the database
     * @param databaseName Name of the database
     * @param username Username for the database
     * @param password Password for the database
     * @throws SQLException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull String host, int port, @Nonnull String databaseName, @Nullable String username, @Nullable String password) throws SQLException {
        this("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password);
    }

    /**
     * Constructs sqlite database by file
     *
     * @param file The file.
     * @throws SQLException If could not connect to the database
     */
    public ConnectionSQL(@Nonnull File file) throws SQLException {
        this("jdbc:sqlite:" + file.getPath(), null, null);
    }

    /**
     * Executes query from the statement
     * Returns null if could not query
     *
     * @param statement The statement
     * @param args Replaces with the ? character
     * @return Result from the query
     */
    @Nullable
    public ResultSet query(String statement, String... args) {
        try {
            connect();

            PreparedStatement ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Executes update from the statement
     * Returns null if could not update
     *
     * @param statement The statement
     * @param args Replaces with the ? character
     * @return Row count or 0
     */
    @Nullable
    public Integer update(@Nonnull String statement, String... args) {
//        Validate.notNull(statement, "Statement cannot be null");

        try {
            connect();

            PreparedStatement ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the connection (could be closed)
     * @return The connection
     */
    @Nullable
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the url (doesn't need to reconnect)
     * @param url The url
     */
    public void setUrl(@Nonnull String url) {
//        Validate.notNull(url, "URL cannot be null");

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
     * Closes the connection
     * @throws SQLException If could not close the connection
     */
    public void close() throws SQLException {
        if (connection == null || connection.isClosed()) return;
        connection.close();
        connection = null;
    }

    /**
     * Creates the connection
     * @throws SQLException If could not create the connection
     */
    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) return;
        connection = DriverManager.getConnection(url, username, password);
    }
}
