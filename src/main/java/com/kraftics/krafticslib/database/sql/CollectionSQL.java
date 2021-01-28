package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.Collection;
import com.kraftics.krafticslib.utils.SQLUtils;
import org.bson.Document;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.util.List;

public class CollectionSQL implements Collection {
    private String name;
    private DatabaseSQL database;

    public CollectionSQL(String name, DatabaseSQL database) {
        this.name = name;
        this.database = database;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Nonnull
    @Override
    public List<Document> find() {
        ResultSet rs = database.connection().query(String.format("SELECT * FROM `%s`", name));
        return SQLUtils.buildDocuments(rs);
    }

    @Nonnull
    @Override
    public List<Document> find(Document filter) {
        ResultSet rs = database.connection().query(String.format("SELECT * FROM `%s` WHERE %s", name, SQLUtils.toCondition(filter)));
        return SQLUtils.buildDocuments(rs);
    }

    @Override
    public void insertOne(Document document) {
        SQLUtils.insert(name, document, database.connection());
        database.connection().close();
    }

    @Override
    public void insertAll(List<Document> documents) {
        for (Document document : documents) {
            SQLUtils.insert(name, document, database.connection());
        }
        database.connection().close();
    }

    @Override
    public void removeAll() {
        database.connection().updateAndClose(String.format("DELETE FROM `%s`", name));
    }

    @Override
    public void removeAll(Document filter) {
        database.connection().updateAndClose(String.format("DELETE FROM `%s` WHERE %s", name, SQLUtils.toCondition(filter)));
    }
}
