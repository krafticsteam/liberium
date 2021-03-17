package com.kraftics.krafticslib.database.utils;

import java.sql.SQLException;

@FunctionalInterface
public interface ConsumerSQL<T> {

    void accept(T o) throws SQLException;
}
