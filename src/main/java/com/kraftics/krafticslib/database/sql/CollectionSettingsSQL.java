package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.CollectionSettings;

public final class CollectionSettingsSQL implements CollectionSettings {
    private final StringBuilder columns;
    private boolean firstColumn;

    public CollectionSettingsSQL() {
        columns = new StringBuilder();
        firstColumn = true;
    }

    public CollectionSettingsSQL addColumn(String name, String type) {
        if (firstColumn) {
            firstColumn = false;
        } else {
            columns.append(", ");
        }

        columns.append('`').append(name).append("` ").append(type);
        return this;
    }

    public String columns() {
        return columns.toString();
    }
}
