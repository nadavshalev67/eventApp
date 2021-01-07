package com.example.eventapp.database.sql_lite;

import android.content.Context;

import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.sql_lite.SQLiteDB;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;

import java.util.List;

public class SQLLiteDataBase extends SQLBase {

    SQLiteDB sqLiteDB;


    public SQLLiteDataBase(Context context) {
        super(context);
        sqLiteDB = new SQLiteDB(context);
    }

    @Override
    public void updateApprovedUser(String id_document, List<String> approvedUser) {

    }

    @Override
    public void insertEvent(Event event, SqlListener sqlListener) {

    }

    @Override
    public void getAllEvents(SqlListenerEvents sqlListenerEvents) {

    }

    @Override
    public void createUser(User user) {

    }
}
