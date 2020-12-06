package eu.kraftics.krafticslib.database;

import java.util.List;

public interface Collection {

    String getName();
    List<DatabaseObject> getObjects();
}
