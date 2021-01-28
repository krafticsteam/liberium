package com.kraftics.krafticslib.database;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface Database {

    @Nullable
    Collection getCollection(String name);

    @Nonnull
    List<Collection> getCollections();

    @Nonnull
    Collection createCollection(String name);

    @Nonnull
    Collection createCollection(String name, CollectionSettings settings);

    boolean removeCollection(String name);

}
