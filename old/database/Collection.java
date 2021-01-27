package com.kraftics.krafticslib.database;

import java.util.List;

/**
 * Collection containing list of {@link DatabaseObject} and a name
 *
 * @see Database
 * @see DatabaseObject
 * @author Panda885
 */
public interface Collection {

    String getName();

    List<DatabaseObject> getObjects();

    void addObject(DatabaseObject object);

    void removeObject(DatabaseObject object);

}
