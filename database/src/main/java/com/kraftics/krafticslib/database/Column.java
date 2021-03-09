package com.kraftics.krafticslib.database;

public class Column {
    private final String type;
    private final String name;
    private final int index;

    public Column(int index, String name, String type) {
        this.type = type;
        this.name = name;
        this.index = index;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public int index() {
        return index;
    }

}
