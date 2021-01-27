package com.kraftics.krafticslib.database;

import org.bson.Document;

import java.util.List;

public interface Collection {

    String getName();

    List<Document> find();

    List<Document> find(Document filter);

    void insertOne(Document document);

    void insertAll(List<Document> document);

    void removeOne(Document filter);

    void removeAll(Document filter);
}
