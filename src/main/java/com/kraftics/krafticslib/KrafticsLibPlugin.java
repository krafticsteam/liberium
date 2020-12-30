package com.kraftics.krafticslib;

import com.kraftics.krafticslib.database.DatabaseException;
import com.kraftics.krafticslib.database.DatabaseObject;
import com.kraftics.krafticslib.database.sql.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class KrafticsLibPlugin extends JavaPlugin {
    private DatabaseSQL database;

    @Override
    public void onEnable() {


        try {
            database = new DatabaseSQL(new ConnectionSQL(new File(getDataFolder(), "database.db")));
            CollectionSQL myTable = database.createCollection("mytable", new Attribute("name", "VARCHAR(16)"), new Attribute("desc", "VARCHAR(16)"));
            if (myTable == null) {
                myTable = database.getCollection("mytable");
                if (myTable == null) {
                    throw new DatabaseException("lmao u r dumb");
                }
            }
            DatabaseObject object = new ObjectSQL().put("name", "Panda885").put("desc", "Legendary programmer.");
            myTable.addObject(object);
            database.updateCollection(myTable);

            for (DatabaseObject o : myTable.getObjects()) {
                System.out.println(o.get("name") + " " + o.get("desc"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
