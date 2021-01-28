package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Collection;
import com.kraftics.krafticslib.database.CollectionSettings;
import com.kraftics.krafticslib.database.Database;
import com.kraftics.krafticslib.database.DatabaseException;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.util.List;

public class DatabaseSQL implements Database {
    private ConnectionSQL connection;

    public DatabaseSQL(@Nonnull ConnectionSQL connection) {
        Validate.notNull(connection, "Connection cannot be null");

        this.connection = connection;
    }

    @Override
    public Collection getCollection(String name) {
        if (!exists(name)) return null;
        return new CollectionSQL(name, this);
    }

    @Nonnull
    @Override
    public List<Collection> getCollections() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Nonnull
    @Override
    public Collection createCollection(String name) {
        return createCollection(name, new CollectionSettingsSQL().addColumn("id", "INT PRIMARY KEY"));
    }

    @Nonnull
    @Override
    public Collection createCollection(String name, CollectionSettings settings) {
        if (settings.getClass() != CollectionSettingsSQL.class) throw new DatabaseException("Invalid collection settings");
        CollectionSettingsSQL settingsSQL = (CollectionSettingsSQL) settings;
        connection.updateAndClose(String.format("CREATE TABLE `%s` (%s)", name, settingsSQL.columns()));
        return new CollectionSQL(name, this);
    }

    @Override
    public boolean removeCollection(String name) {
        try {
            connection.updateAndClose(String.format("DROP TABLE `%s`", name));
            return true;
        } catch (DatabaseException e) {
            return false;
        }
    }

    public boolean exists(String name) {
        try {
            connection.queryAndClose(rs -> {}, String.format("SELECT * FROM `%s`", name));
            return true;
        } catch (DatabaseException e) {
            return false;
        }
    }

    public ConnectionSQL connection() {
        return connection;
    }
}
