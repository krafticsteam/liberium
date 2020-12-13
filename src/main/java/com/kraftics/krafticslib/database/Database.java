package eu.kraftics.krafticslib.database;

import java.util.List;

/**
 * Database make by JSON like collections.
 * By using <code>pull()</code>, you receive the collections from the remote database. You can get the collections by <code>getCollections()</code>
 * or designated collection with <code>getCollection(name)</code>.
 * You can use <code>createCollection(name)</code> to create a collection by name.
 * To delete a collection use <code>removeCollection(name)</code> with the collection name.
 * Use <code>push()</code> to write the collections to the remote database.
 *
 * @see Collection
 * @see DatabaseObject
 * @see DatabaseSerializable
 * @param <T> Collection type using the database
 * @author Panda885s
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
     * Pushes collcetions to the database
     */
    void push();

    /**
     * Pulls collections from the database
     */
    void pull();
}
