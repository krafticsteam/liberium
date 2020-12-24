package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Database;
import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.utils.Tuple;
//import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SQL database
 *
 * @author Panda885
 */
public class DatabaseSQL implements Database<CollectionSQL> {
    private ConnectionSQL connection;

    public DatabaseSQL(@Nonnull ConnectionSQL connection) {
//        Validate.notNull(connection, "Connection cannot be null");

        this.connection = connection;
    }

    @Override
    public CollectionSQL getCollection(String name) {
        ResultSet set = connection.query(String.format("SELECT * FROM `%s`", name));
        if (set == null) return null;

        try {
            return collectionFrom(name, set);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CollectionSQL> getCollections() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CollectionSQL createCollection(String name) {
        return createCollection(name, new ArrayList<>());
    }

    public CollectionSQL createCollection(String name, Attribute... attributes) {
        return createCollection(name, Arrays.asList(attributes));
    }

    public CollectionSQL createCollection(String name, List<Attribute> attributes) {
        List<Attribute> newAttributes = new ArrayList<>(attributes);

        Integer result = connection.update(String.format("CREATE TABLE `%s` (%s)", name, toString(attributes)));
        if (result == null) return null;
        return new CollectionSQL(name, new ArrayList<>());
    }

    @Override
    public void removeCollection(String name) {
        connection.update(String.format("DROP TABLE `%s`", name));
    }

    public void updateCollection(CollectionSQL collection) {
        connection.update(String.format("DELETE FROM `%s`", collection.getName()));
        for (DatabaseObject object : collection.getObjects()) {
            Map<String, Object> map = object.serialize();
            if (map == null) continue;
            Tuple<String, List<String>> tuple = toString(map);
            System.out.println(tuple.getFirst());
            for (String s : tuple.getSecond()) {
                System.out.println(" - " + s);
            }
            connection.update(String.format("INSERT INTO `%s` %s", collection.getName(), tuple.getFirst()), tuple.getSecond().toArray(new String[0]));
        }
    }

    @Override
    public void push() {

    }

    @Override
    public void pull() {

    }

    private String toString(List<Attribute> attributes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            builder.append(attribute.getName()).append(" ").append(attribute.getType());
            if (i < attributes.size() - 1) builder.append(",");
        }
        return builder.toString();
    }

    private Tuple<String, List<String>> toString(Map<String, Object> map) {
        StringBuilder names = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        List<String> valueList = new ArrayList<>();

        AtomicInteger i = new AtomicInteger();
        map.forEach((name, object) -> {
            names.append(name);
            values.append("?");
            valueList.add(object.toString());

            if (i.incrementAndGet() < map.size() - 1) {
                names.append(", ");
                values.append(", ");
            }
        });
        names.append(")");
        values.append(")");

        return new Tuple<>(names.toString() + " VALUES " + values.toString(), valueList);
    }

    private CollectionSQL collectionFrom(String name, ResultSet set) throws DatabaseException {
        try {
            ResultSetMetaData meta = set.getMetaData();
            int columnCount = meta.getColumnCount();

            List<DatabaseObject> objects = new ArrayList<>();
            while (set.next()) {
                DatabaseObject object = new ObjectSQL();

                for (int i = 1; i <= columnCount; i++) {
                    object.put(meta.getColumnName(i), set.getObject(i));
                }

                objects.add(object);
            }

            return new CollectionSQL(name, objects);
        } catch (Exception e) {
            throw new DatabaseException("Could not create Collection from ResultSet", e);
        }
    }

    public ConnectionSQL getConnection() {
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        DatabaseSQL database = new DatabaseSQL(new ConnectionSQL(new File("test.db")));
        ResultSet rs = database.getConnection().query("SELECT * FROM test");
        if (rs == null) {
            System.out.println("oh no");
            return;
        }

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        System.out.println(columnCount);
    }
}