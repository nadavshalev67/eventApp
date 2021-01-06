package com.example.eventapp.database;

import android.content.Context;

import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.utitlities.Utilities;

public class SQLHolder {

    private static SQLBase mInstance = null;
    public static Context context;


    // private constructor restricted to this class itself
    private SQLHolder() {
    }

    // static method to create instance of Singleton class
    public static SQLBase getInstance() {
        if (mInstance == null) {
            mInstance = new FireStoreSql(Utilities.getAppContenxt());
        }
        return mInstance;
    }


}
