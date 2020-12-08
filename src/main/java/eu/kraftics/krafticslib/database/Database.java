package eu.kraftics.krafticslib.database;

import java.util.List;

public interface Database<T extends Collection> {
    T getCollection(String name);
    List<T> getCollections();
    T createCollection(String name);
    void removeCollection(String name);

    void push();
    void pull();
}
