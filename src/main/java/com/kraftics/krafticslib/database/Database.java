package com.kraftics.krafticslib.database;

import java.util.List;

/**
 * By using {@link #pull()}, you receive the collections from the remote database. You can get the collections by {@link #getCollections()}
 * or designated collection with {@link #getCollection(String)}.
 * You can use {@link #createCollection(String)} to create a collection by name.
 * To delete a collection use {@link #removeCollection(String)} with the collection name.
 * Use {@link #push()} to write the collections to the remote database.
 *
 * @see Collection
 * @see DatabaseObject
 * @see DatabaseSerializable
 * @param <T> Collection type using the database
 * @author Panda885
 */
public interface Database<T extends Collection> {
    /**
     * Gets collection by name
     *
     * @param name Name of the collection
     * @return The collection
     */
    T getCollection(String name);

    /**
     * Gets all collections
     *
     * @return collections
     */
    List<T> getCollections();

    /**
     * Creates collection by name
     *
     * @param name Name of the collection
     * @return The collection
     */
    T createCollection(String name);

    /**
     * Removes collection by name
     *
     * @param name Name of the collection
     */
    void removeCollection(String name);

    /**
     * Pushes collections to the database
     */
    void push();

    /**
     * Pulls collections from the database
     */
    void pull();
}
