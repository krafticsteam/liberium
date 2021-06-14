package com.kraftics.liberium.database.cache;

import com.kraftics.liberium.database.Column;
import com.kraftics.liberium.database.Document;
import com.kraftics.liberium.database.Table;
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
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull List<Column> getColumns() {
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
    public Table remove(Document document) {
        documents.remove(document);
        return this;
    }

    @Override
    public Table remove() {
        documents.clear();
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
