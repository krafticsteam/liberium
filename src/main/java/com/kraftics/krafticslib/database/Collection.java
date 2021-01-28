package com.kraftics.krafticslib.database;

import org.bson.Document;

import javax.annotation.Nonnull;
import java.util.List;

public interface Collection {

    @Nonnull
    String name();

    @Nonnull
    List<Document> find();

    @Nonnull
    List<Document> find(Document filter);

    void insertOne(Document document);

    void insertAll(List<Document> document);

    void removeAll();

    void removeAll(Document filter);
}
