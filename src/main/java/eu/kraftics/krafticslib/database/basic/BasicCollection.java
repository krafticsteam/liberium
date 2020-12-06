package eu.kraftics.krafticslib.database.basic;

import eu.kraftics.krafticslib.database.Collection;
import eu.kraftics.krafticslib.database.DatabaseObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasicCollection implements Collection {
    private List<DatabaseObject> objects;
    private String name;

    public BasicCollection(String name, List<DatabaseObject> objects) {
        this.objects = objects;
        this.name = name;
    }

    public BasicCollection(String name) {
        this(name, new LinkedList<>());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<DatabaseObject> getObjects() {
        return objects;
    }

    public void addObject(DatabaseObject object) {
        objects.add(object);
    }

    public void removeObject(DatabaseObject object) {
        objects.remove(object);
    }
}
