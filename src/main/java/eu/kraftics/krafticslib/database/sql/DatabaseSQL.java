package eu.kraftics.krafticslib.database.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseSQL {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public ResultSet query(String statement, String... args) {
        try {
            PreparedStatement ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean execute(String statement, String... args) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(statement);
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                ps.setString(i+1, arg);
            }
            return !ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}