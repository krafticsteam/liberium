package com.kraftics.krafticslib.utils;

import java.sql.SQLException;
import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerSQL<T> {

    void accept(T o) throws SQLException;
}
