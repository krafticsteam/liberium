package eu.kraftics.krafticslib.database;

import java.util.List;

public interface Database {
    Collection getCollection(String name);
    List<Collection> getCollections();
    Collection createCollection(String name);
    void removeCollection(String name);

    void push();
    void pull();
}
