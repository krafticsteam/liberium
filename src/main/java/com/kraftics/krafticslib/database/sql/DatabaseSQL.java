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
 * Here the collections are not saved in memory
 * so you don't need to use {@link #push()} and {@link #pull()} methods.
 * When getting, creating, updating or removing a collection it will
 * execute one or more statements.
 *
 * @author Panda885
 */
public class DatabaseSQL implements Database<CollectionSQL> {
    private ConnectionSQL connection;

    public DatabaseSQL(@Nonnull ConnectionSQL connection) {
        Validate.notNull(connection, "Connection cannot be null");

        this.connection = connection;
    }

    /**
     * Gets collection by name
     *
     * @param name Name of the collection
     * @return The collection
     */
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

    /**
     * Gets all collections. NOT SUPPORTED IN SQL DATABASE!
     *
     * @throws UnsupportedOperationException NOT SUPPORTED
     * @return collections
     */
    @Override
    public List<CollectionSQL> getCollections() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates collection by name
     *
     * @param name Name of the collection
     * @return The collection
     */
    @Override
    public CollectionSQL createCollection(String name) {
        return createCollection(name, new ArrayList<>());
    }

    /**
     * Creates collection by name and attributes
     *
     * @param name Name of the collection
     * @param columns Attributes of the collection
     * @return The collection
     */
    public CollectionSQL createCollection(String name, Column... columns) {
        return createCollection(name, Arrays.asList(columns));
    }

    /**
     * Creates collection by name and attributes
     *
     * @param name Name of the collection
     * @param columns Attributes of the collection
     * @return The collection
     */
    public CollectionSQL createCollection(String name, List<Column> columns) {
        try {
            connection.update(String.format("CREATE TABLE `%s` %s", name, SQLUtils.toString(columns)));
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

    /**
     * Removes collection by name
     *
     * @param name Name of the collection
     */
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

    /**
     * Updates the collection
     *
     * @param collection The collection to update
     */
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

    /**
     * Gets the connection using the database
     *
     * @return The connection
     */
    public ConnectionSQL getConnection() {
        return connection;
    }
}