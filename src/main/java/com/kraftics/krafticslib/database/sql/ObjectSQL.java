package com.kraftics.krafticslib.database.sql;

import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.database.DatabaseSerializable;

import java.util.HashMap;
import java.util.Map;

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
}
