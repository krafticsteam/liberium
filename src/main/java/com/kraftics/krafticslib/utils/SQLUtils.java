package com.kraftics.krafticslib.utils;

import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.database.sql.CollectionSQL;
import com.kraftics.krafticslib.database.sql.ConnectionSQL;
import com.kraftics.krafticslib.database.sql.ObjectSQL;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SQLUtils {

    private SQLUtils() {
        throw new UnsupportedOperationException();
    }

    public static CollectionSQL createCollection(String name, ConnectionSQL con) throws DatabaseException {
        try {
            List<DatabaseObject> objects = new ArrayList<>();

            ResultSet rs = con.query(String.format("SELECT * FROM `%s`", name));
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                DatabaseObject o = new ObjectSQL();
                for (int i = 1; i <= columnCount; i++) {
                    o.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                objects.add(o);
            }

            con.close();
            return new CollectionSQL(name, objects);
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }
}
