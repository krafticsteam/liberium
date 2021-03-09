package com.kraftics.krafticslib.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Database {

    @Nullable
    Table table(String name);

    @NotNull
    Table createTable(String name, Table.Settings settings);

    @NotNull
    default Table createTable(String name) {
        return createTable(name, Table.settings());
    }

    void deleteTable(String name);

}
