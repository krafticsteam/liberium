package com.kraftics.krafticslib.database;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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

    Table remove(Predicate<Document> predicate);

    Table add(Document document);

    void update();

    @NotNull
    static Settings settings() {
        return new Settings();
    }

    class Settings {
        private final List<Column> columns = new ArrayList<>();

        public Settings column(Column column) {
            this.columns.add(column);
            return this;
        }

        public Settings column(String name, String type) {
            return column(new Column(name, type));
        }

        public List<Column> columns() {
            return columns;
        }

        public String build() {
            StringJoiner joiner = new StringJoiner(", ");
            for (Column column : columns) joiner.add(column.name() + " " + column.type());
            return "(" + joiner + ")";
        }
    }
}
