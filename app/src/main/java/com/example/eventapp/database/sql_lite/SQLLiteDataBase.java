package com.example.eventapp.database.sql_lite;

import android.content.Context;

import com.example.eventapp.database.SQLBase;
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
        List<User> list = sqLiteDB.getAllUsers();
        listener.onGetAllUsers(list);

    }

    @Override
    public void updateRejectedUser(String id_document, List<String> rejectedUser) {

    }


    @Override
    public void removeEvent(String id_documented) {
        Event item = null;
        if(sqLiteDB.readEvent(item.id_document).equals(id_documented)) {
            sqLiteDB.deleteEvent(item);
        }
    }

    @Override
    public void updateEvent(String id_document, Event event, SqlListener listener) {


    }

    @Override
    public void insertComment(Comment comment, SqlListener sqlListener) {
        if(sqLiteDB != null){
            sqLiteDB.addComment(comment);
        }
        sqlListener.onCompleteListener();


    }

    @Override
    public void getAllCommentsOfOneEvent(String event_id, SqlListenerComments listener) {

    }

    @Override
    public void removeComment(String commentID) {
        Comment item = null;
        if(sqLiteDB.readComment(item.commentID).equals(commentID)){
            sqLiteDB.deleteComment(item);

        }

    }

    @Override
    public void insertEvent(String createdBy, Event event, SqlListener sqlListener) {

    }


    @Override
    public void getAllEvents(SqlListenerEvents sqlListenerEvents) {
        List<Event> list = sqLiteDB.getAllEvents();
        sqlListenerEvents.onGetAllEvents(list);


    }

    @Override
    public void createUser(User user) {
        if(sqLiteDB != null){

        }

    }
}
