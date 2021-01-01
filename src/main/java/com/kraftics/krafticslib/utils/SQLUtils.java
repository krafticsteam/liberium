package com.kraftics.krafticslib.utils;

import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.database.sql.Attribute;
import com.kraftics.krafticslib.database.sql.CollectionSQL;
import com.kraftics.krafticslib.database.sql.ConnectionSQL;
import com.kraftics.krafticslib.database.sql.ObjectSQL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SQLUtils {

    private SQLUtils() {
        throw new UnsupportedOperationException();
    }

    public static void checkForDrivers() throws DatabaseException {
        try {
            Class.forName("org.sqlite.JDBC");
            Class.forName("java.sql.DriverManager");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Could not find drivers", e);
        }
    }

    public static String toString(List<Attribute> attributes) {
        StringBuilder sb = new StringBuilder("(");

        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            sb.append('`').append(attribute.getName().replace(' ', '_')).append("` ").append(attribute.getType());
            if (i < attributes.size() - 1) sb.append(", ");
        }

        return sb.append(")").toString();
    }

    public static void insert(String name, DatabaseObject o, ConnectionSQL con) throws DatabaseException {
        Map<String, Object> map = o.serialize();
        List<Object> values = new ArrayList<>();

        StringBuilder sbNames = new StringBuilder(String.format("INSERT INTO `%s` (", name));
        StringBuilder sbValues = new StringBuilder(") VALUES (");

        Set<Map.Entry<String, Object>> entries = map.entrySet();
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

    public static CollectionSQL buildCollection(String name, ConnectionSQL con) throws DatabaseException {
        List<DatabaseObject> objects = new ArrayList<>();
        ResultSet rs = con.query(String.format("SELECT * FROM `%s`", name));
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                DatabaseObject o = new ObjectSQL();
                for (int i = 1; i <= columnCount; i++) {
                    o.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                objects.add(o);
            }

            return new CollectionSQL(name, objects);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }
}
