package com.mjebooksdk.util;

import com.mjebooksdk.database.dao.IReadLocationDao;
import com.mjebooksdk.database.dao.MockReadLocationImpl;
import com.mjebooksdk.database.dao.ReadLocationImpl;

public class DatabaseManager {

    private static IReadLocationDao instance;

    private DatabaseManager() {
    }

    public static IReadLocationDao getInstance(){
        if (instance == null){
            instance = new ReadLocationImpl();
        }
        return instance;
    }

}
