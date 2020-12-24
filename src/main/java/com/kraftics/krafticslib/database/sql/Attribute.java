package com.kraftics.krafticslib.database.sql;

public class Attribute {
    private String name;
    private String type;

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
