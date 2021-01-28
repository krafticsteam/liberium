package com.kraftics.krafticslib.utils;

import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.sql.ConnectionSQL;
import org.bson.Document;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SQLUtils {

    /**
     * Builds list of documents from a ResultSet
     *
     * @param rs the ResultSet
     * @return list of documents
     * @throws DatabaseException If something went wrong
     */
    public static List<Document> buildDocuments(ResultSet rs) {
        try {
            List<Document> documents = new ArrayList<>();

            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            List<String> columnNames = new ArrayList<>();

            for (int column = 1; column <= columns; column++) {
                columnNames.add(meta.getColumnLabel(column));
            }

            while (rs.next()) {
                Document document = new Document();
                for (String column : columnNames) {
                    document.put(column, rs.getObject(column));
                }
                documents.add(document);
            }

            return documents;
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Makes sql WHERE condition from a document
     *
     * @param document the Document
     * @return a condition
     */
    public static String toCondition(Document document) {
        StringBuilder builder = new StringBuilder();

        Set<Map.Entry<String, Object>> entries = document.entrySet();
        int i = 0;
        for (Map.Entry<String, Object> entry : entries) {
            builder.append('`').append(entry.getKey()).append("`='").append(entry.getValue()).append("'");

            if (i++ < entries.size() - 1) {
                builder.append(" AND ");
            }
        }

        return builder.toString();
    }

    /**
     * Insert a document to table
     *
     * @param name Name of the table
     * @param document the Document
     * @param con a Connection
     * @throws DatabaseException If something went wrong
     */
    public static void insert(String name, Document document, ConnectionSQL con) {
        List<Object> values = new ArrayList<>();

        StringBuilder sbNames = new StringBuilder(String.format("INSERT INTO `%s` (", name));
        StringBuilder sbValues = new StringBuilder(") VALUES (");

        Set<Map.Entry<String, Object>> entries = document.entrySet();
        int i = 0;
        for (Map.Entry<String, Object> entry : entries) {
            values.add(entry.getValue());
            sbNames.append('`').append(entry.getKey()).append('`');
            sbValues.append("?");

            if (i++ < entries.size() - 1) {
                sbNames.append(", ");
                sbValues.append(", ");
            }
        }
        sbValues.append(")");

        con.update(sbNames.append(sbValues).toString(), values.toArray(new Object[0]));
    }
}
