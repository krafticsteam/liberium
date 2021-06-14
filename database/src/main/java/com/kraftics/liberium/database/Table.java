package com.kraftics.liberium.database;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

/**
 * This represents a sql table
 */
public interface Table {

    /**
     * @return Name of this table
     */
    @NotNull
    String getName();

    /**
     * @return List of columns in this table <i>{@link Column#Column(String, String) (see constructor javadoc)}</i>
     */
    @NotNull
    List<Column> getColumns();

    /**
     * Finds all documents in this table
     *
     * @return List of documents
     */
    @NotNull
    List<Document> find();

    /**
     * Finds all documents matching the predicate in this table
     *
     * @param predicate The predicate
     * @return List of documents
     */
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

    /**
     * Removes all documents matching the predicate
     *
     * @param predicate The predicate
     * @return This table
     */
    Table remove(Predicate<Document> predicate);

    /**
     * Removes a document from this table
     *
     * @param document The document
     * @return This table
     */
    Table remove(Document document);

    /**
     * Removes all documents from this table
     *
     * @return This table
     */
    Table remove();

    /**
     * Adds a document to this table
     *
     * @param document The document
     * @return This table
     */
    Table add(Document document);

    /**
     * Adds all documents from collection to this table
     *
     * @param documents The document collection
     * @return This table
     */
    default Table add(Collection<Document> documents) {
        documents.forEach(this::add);
        return this;
    }

    /**
     * Updates this table.
     * This will normally use the {@link Database#updateTable(Table)} method
     */
    void update();

    /**
     * Creates a new settings instance
     *
     * @return The settings instance
     */
    @NotNull
    static Settings settings() {
        return new Settings();
    }

    class Settings {
        private final List<Column> columns = new ArrayList<>();

        /**
         * Adds a column to this database
         *
         * @param column The column
         * @return This settings
         */
        public Settings column(Column column) {
            this.columns.add(column);
            return this;
        }

        /**
         * Adds a column to this database
         *
         * @param name The column name
         * @param type The column type
         * @return This settings
         */
        public Settings column(String name, String type) {
            return column(new Column(name, type));
        }

        public List<Column> columns() {
            return columns;
        }

        public String build() {
            StringJoiner joiner = new StringJoiner(", ");
            for (Column column : columns) joiner.add(column.getName() + " " + column.getType());
            return "(" + joiner + ")";
        }
    }
}
