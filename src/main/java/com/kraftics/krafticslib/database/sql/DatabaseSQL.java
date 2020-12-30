package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Database;
import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.utils.SQLUtils;
import com.kraftics.krafticslib.utils.Tuple;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
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
        Validate.notNull(connection, "Connection cannot be null");

        this.connection = connection;
    }

    @Override
    public CollectionSQL getCollection(String name) {
        try {
            return SQLUtils.createCollection(name, connection);
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
        Integer result = connection.update(String.format("CREATE TABLE `%s` (%s)", name, toString(new ArrayList<>(attributes))));
        if (result == null) return null;
        return new CollectionSQL(name);
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

            if (i.getAndIncrement() < map.size() - 1) {
                names.append(", ");
                values.append(", ");
            }
        });
        names.append(")");
        values.append(")");

        return new Tuple<>(names.toString() + " VALUES " + values.toString(), valueList);
    }

    public ConnectionSQL getConnection() {
        return connection;
    }
}