package com.kraftics.krafticslib.database.sql;

import java.io.File;
import java.sql.*;

/**
 * SQL database
 *
 * @author Panda885
 */
public class DatabaseSQL {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    /**
     * Constructs sqlite database by file
     *
     * @param file The file.
     * @throws SQLException If could not connect to the database
     */
    public DatabaseSQL(File file) throws SQLException {
        this("jdbc:sqlite:" + file.getPath(), null, null);
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
    public DatabaseSQL(String host, int port, String databaseName, String username, String password) throws SQLException {
        this("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password);
    }

    /**
     * Constructs mysql database by url, username and password
     *
     * @param url Url to the database
     * @param username Username for the database
     * @param password Password for the database
     * @throws SQLException If could not connect to the database
     */
    public DatabaseSQL(String url, String username, String password) throws SQLException {
        if (url == null) throw new NullPointerException("Url cannot be null");

        this.url = url;
        this.username = username;
        this.password = password;

        connect();
        close();
    }

    /**
     * Executes query from the statement
     * Returns null if could not query
     *
     * @param statement The statement
     * @param args Replaces with the ? character
     * @return Result from the query
     */
    public ResultSet query(String statement, String... args) {
        PreparedStatement ps = null;
        try {
            connect();

            ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

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
    public Integer update(String statement, String... args) {
        PreparedStatement ps = null;
        try {
            connect();

            ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the url (doesn't need to reconnect)
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the username (doesn't need to reconnect)
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password (doesn't need to reconnect)
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the url
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the connection (could be closed)
     * @return The connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection
     * @throws SQLException If could not close the connection
     */
    public void close() throws SQLException {
        if (connection.isClosed()) return;
        connection.close();
    }

    /**
     * Creates the connection
     * @throws SQLException If could not create the connection
     */
    public void connect() throws SQLException {
        if (!connection.isClosed()) return;
        connection = DriverManager.getConnection(url, username, password);
    }
}