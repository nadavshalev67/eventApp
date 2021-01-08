package com.example.eventapp.database.sql_lite;

import android.content.Context;

import com.example.eventapp.database.SQLBase;
import com.example.eventapp.database.sql_lite.SQLiteDB;
import com.example.eventapp.models.Comment;
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
    public void updateApprovedUser(Event enent, String uuidOfUserApproved) {

    }

    @Override
    public void getAllUsers(SqlListenerUsers listener) {

    }

    @Override
    public void updateRejectedUser(String id_document, List<String> rejectedUser) {

    }


    @Override
    public void removeEvent(String id_document) {

    }

    @Override
    public void updateEvent(String id_document, Event event, SqlListener listener) {

    }

    @Override
    public void insertComment(Comment comment, SqlListener sqlListener) {

    }

    @Override
    public void getAllCommentsOfOneEvent(String event_id, SqlListenerComments listener) {

    }

    @Override
    public void insertEvent(String createdBy, Event event, SqlListener sqlListener) {

    }


    @Override
    public void getAllEvents(SqlListenerEvents sqlListenerEvents) {

    }

    @Override
    public void createUser(User user) {

    }
}
