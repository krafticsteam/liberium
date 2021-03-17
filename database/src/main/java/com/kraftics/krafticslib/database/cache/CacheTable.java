package com.kraftics.krafticslib.database.cache;

import com.kraftics.krafticslib.database.Column;
import com.kraftics.krafticslib.database.Document;
import com.kraftics.krafticslib.database.Table;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class CacheTable implements Table {
    private final CacheDatabase database;
    private final String name;
    private final List<Document> documents;
    private final List<Column> columns;

    public CacheTable(CacheDatabase database, String name, List<Document> documents, List<Column> columns) {
        this.database = database;
        this.name = name;
        this.documents = documents;
        this.columns = columns;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull List<Column> columns() {
        return new ArrayList<>(columns);
    }

    @Override
    public @NotNull List<Document> find() {
        return new ArrayList<>(documents);
    }

    @Override
    public Table remove(Predicate<Document> predicate) {
        documents.removeIf(predicate);
        return this;
    }

    @Override
    public Table add(Document document) {
        documents.add(document);
        return this;
    }

    @Override
    public void update() {
        this.database.updateTable(this);
    }
}
