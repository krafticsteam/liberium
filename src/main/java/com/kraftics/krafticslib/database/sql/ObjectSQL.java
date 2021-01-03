package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.database.DatabaseSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectSQL implements DatabaseObject {
    private Map<String, Object> map = new HashMap<>();

    @Override
    public DatabaseObject put(String name, Object o) {
        map.put(name, o);
        return this;
    }

    @Override
    public Object get(String name) {
        return map.get(name);
    }

    @Override
    public Map<String, Object> serialize() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectSQL)) return false;
        ObjectSQL objectSQL = (ObjectSQL) o;
        return map.equals(objectSQL.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
