package com.kraftics.krafticslib.database.sql;

import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Column {
    private String name;
    private String type;

    /**
     * Creates column used by {@link DatabaseSQL SQL Database} to create a {@link CollectionSQL Collection}.
     *
     * @param name The name of the column
     * @param type The data type of the column
     */
    public Column(@Nonnull String name, @Nonnull String type) {
        Validate.notNull(name, "Name cannot be null");
        Validate.notNull(type, "Type cannot be null");

        this.name = name;
        this.type = type;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getType() {
        return type;
    }

    @Override
    @Nonnull
    public String toString() {
        return '`' + name + "` " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return name.equals(column.name) && type.equals(column.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
