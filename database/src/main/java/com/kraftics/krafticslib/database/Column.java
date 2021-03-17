package com.kraftics.krafticslib.database;

public class Column {
    private final String type;
    private final String name;

    public Column(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

}
