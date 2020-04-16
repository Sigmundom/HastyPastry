package com.mygdx.hastypastry.singletons;

import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;

/**
 * Holds referance to DB object
 */
public class DBManager {
    public static final DBManager instance = new DBManager();
    private HastyPastryDatabase db;

    //Singleton - prevents instantiation from other classes
    private DBManager() {
    }

    public HastyPastryDatabase getDB() {
        return db;
    }

    public void init(HastyPastryDatabase db) {
        this.db = db;
    }
}
