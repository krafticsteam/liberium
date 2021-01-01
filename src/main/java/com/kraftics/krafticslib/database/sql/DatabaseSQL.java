package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Database;
import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.utils.SQLUtils;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            return SQLUtils.buildCollection(name, connection);
        } catch (DatabaseException e) {
            return null;
        } finally {
            try {
                connection.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
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
        try {
            connection.update(String.format("CREATE TABLE `%s` %s", name, SQLUtils.toString(attributes)));
            return new CollectionSQL(name);
        } catch (DatabaseException e) {
            return null;
        } finally {
            try {
                connection.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeCollection(String name) {
        try {
            connection.update(String.format("DROP TABLE `%s`", name));
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCollection(CollectionSQL collection) {
        try {
            String name = collection.getName();
            connection.update(String.format("DELETE FROM `%s`", name));

            for (DatabaseObject object : collection.getObjects()) {
                SQLUtils.insert(name, object, connection);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void push() {

    }

    @Override
    public void pull() {

    }

    public ConnectionSQL getConnection() {
        return connection;
    }
}