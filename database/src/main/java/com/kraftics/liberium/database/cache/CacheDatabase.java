package com.kraftics.liberium.database.cache;

import com.kraftics.liberium.database.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class CacheDatabase implements Database {
    private final Connection connection;

    public CacheDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public @Nullable Table getTable(String name) {
        try {
            return buildTable(name, connection.query("SELECT * FROM `" + name + "`"));
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public @NotNull Table createTable(String name, Table.Settings settings) {
        try {
            connection.updateAndClose("CREATE TABLE `" + name + "` " + settings.build());
            return new CacheTable(this, name, new ArrayList<>(), settings.columns());
        } catch (SQLException e) {
            throw new DatabaseException("Could not create a table", e);
        }
    }

    @Override
    public void deleteTable(String name) {
        try {
            connection.updateAndClose("DROP TABLE `" + name + "`");
        } catch (SQLException e) {
            throw new DatabaseException("Could not delete a table", e);
        }
    }

    @Override
    public boolean hasTable(String name) {
        try {
            connection.query("SELECT * FROM `" + name + "`");
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateTable(Table table) {
        if (!hasTable(table.getName()) || table.getClass() != CacheTable.class) throw new IllegalArgumentException("Invalid table type");
        try {
            connection.update("DELETE FROM `" + table.getName() + '`');
            for (Document document : table.find()) {
                try {
                    insert(table.getName(), document);
                } catch (SQLException e) {
                    new DatabaseException("Could not insert document", e).printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not update table", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    private CacheTable buildTable(String name, ResultSet set) throws SQLException {
        ResultSetMetaData metaData = set.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<Document> documents = new ArrayList<>();
        List<Column> columns = new ArrayList<>();

        for (int i = 1; i <= columnCount; i++) {
            columns.add(new Column(metaData.getColumnName(i), metaData.getColumnTypeName(i)));
        }

        while (set.next()) {
            Document document = new Document();
            for (Column column : columns) {
                document.add(column.getName(), set.getObject(column.getName()));
            }
            documents.add(document);
        }

        return new CacheTable(this, name, documents, columns);
    }

    private void insert(String name, Document document) throws SQLException {
        Map<String, Object> map = document.serialize();
        ArrayList<Object> values = new ArrayList<>();
        StringJoiner columnBuilder = new StringJoiner(", ");
        StringJoiner valuesBuilder = new StringJoiner(", ");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            valuesBuilder.add("?");
            values.add(entry.getValue());

            columnBuilder.add(entry.getKey());
        }

        connection.update("INSERT INTO `" + name + "` (" + columnBuilder + ") VALUES (" + valuesBuilder + ")", values.toArray());
    }
}
