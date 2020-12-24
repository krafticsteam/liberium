package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Collection;
import com.kraftics.krafticslib.database.DatabaseObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<DatabaseObject> getObjects() {
        return new ArrayList<>(objects);
    }

    @Override
    public void addObject(DatabaseObject object) {
        objects.add(object);
    }

    @Override
    public void removeObject(DatabaseObject object) {
        objects.remove(object);
    }

    public DatabaseObject getObjectBy(Function<DatabaseObject, Boolean> fun) {
        for (DatabaseObject object : objects) {
            if (fun.apply(object)) {
                return object;
            }
        }
        return null;
    }

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
