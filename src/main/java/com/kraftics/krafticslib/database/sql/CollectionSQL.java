package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Collection;
import com.kraftics.krafticslib.database.DatabaseObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * SQL Collection containing list of objects
 *
 * @author Panda885
 */
public class CollectionSQL implements Collection {
    private final List<DatabaseObject> objects;
    private final String name;

    public CollectionSQL(String name, List<DatabaseObject> objects) {
        this.name = name;
        this.objects = objects;
    }

    public CollectionSQL(String name, DatabaseObject... objects) {
        this(name, new ArrayList<>(Arrays.asList(objects)));
    }

    /**
     * Gets the name of the collection
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets all objects
     */
    @Override
    public List<DatabaseObject> getObjects() {
        return new ArrayList<>(objects);
    }

    /**
     * Adds object to the collection
     *
     * @param object The object to add
     */
    @Override
    public void addObject(DatabaseObject object) {
        objects.add(object);
    }

    /**
     * Removes object from the collection
     *
     * @param object The object to remove
     */
    @Override
    public void removeObject(DatabaseObject object) {
        objects.remove(object);
    }

    /**
     * Gets object by a function
     *
     * @param fun The function
     * @return The object
     */
    public DatabaseObject getObjectBy(Function<DatabaseObject, Boolean> fun) {
        for (DatabaseObject object : objects) {
            if (fun.apply(object)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Gets list of objects by a function
     *
     * @param fun The function
     * @return The list
     */
    public List<DatabaseObject> getObjectsBy(Function<DatabaseObject, Boolean> fun) {
        List<DatabaseObject> list = new ArrayList<>();
        for (DatabaseObject object : objects) {
            if (fun.apply(object)) {
                list.add(object);
            }
        }
        return list;
    }
}
