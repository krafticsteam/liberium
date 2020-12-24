package com.kraftics.krafticslib.database;

import java.util.List;

/**
 * Collection containing list of <code>DatabaseObject</code> and a name
 *
 * @see Database
 * @see DatabaseObject
 * @author Panda885
 */
public interface Collection {

    /**
     * Gets the collection name
     *
     * @return What do you think?
     */
    String getName();

    /**
     * Gets the objects in the collection
     *
     * @return Hmmm... I don't know.
     */
    List<DatabaseObject> getObjects();

    void addObject(DatabaseObject object);

    void removeObject(DatabaseObject object);

}
