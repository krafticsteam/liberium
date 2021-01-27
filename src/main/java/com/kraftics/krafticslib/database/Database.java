package com.kraftics.krafticslib.database;

import java.util.List;

public interface Database {

    Collection getCollection(String name);

    Collection createCollection(String name);

    List<Collection> getCollections();

    boolean removeCollection(String name);

}
