package com.kraftics.krafticslib.database;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface Table {

    @NotNull
    String name();

    @NotNull
    List<Column> columns();

    @NotNull
    List<Document> find();

    @NotNull
    default List<Document> find(Predicate<Document> predicate) {
        List<Document> list = new ArrayList<>();
        for (Document document : find()) {
            if (predicate.test(document)) {
                list.add(document);
            }
        }
        return list;
    }

    @NotNull
    static Settings settings() {
        return new Settings();
    }

    class Settings {
        private List<Column> columns;
    }
}
