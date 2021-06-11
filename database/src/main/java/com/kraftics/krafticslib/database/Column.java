package com.kraftics.krafticslib.database;

/**
 * This class represents a sql database column
 */
public class Column {
    private final String type;
    private final String name;

    /**
     * Creates new instance.
     *
     * <p>
     * The type is different when creating a table and querying a table.
     * When creating it should be full type (for example: <i>INT NOT NULL PRIMARY KEY</i>), when querying it will be just the type (for example: <i>INT</i>)
     * </p>
     *
     * @param name The name of this column
     * @param type The type of this column <i>(read above)</i>
     */
    public Column(String name, String type) {
        this.type = type;
        this.name = name;
    }

    /**
     * @return The name of this column
     */
    public String getName() {
        return name;
    }

    /**
     * @return The type of this column <i>(read above)</i>
     */
    public String getType() {
        return type;
    }

}
