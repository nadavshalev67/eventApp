package com.example.eventapp.database;

import android.content.Context;

import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.models.Comment;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;

import java.util.List;

public abstract class SQLBase {
    protected String EVENT_TABLE_NAME = "event";
    protected String COMMENT_TABLE_NAME = "Comment";
    protected String USER_TABLE_NAME = "Users";

    public abstract void updateApprovedUser(Event enent, String uuidOfUserApproved);

    public abstract void getAllUsers(SqlListenerUsers listener);

    public abstract void updateRejectedUser(String id_document, List<String> rejectedUser);

    //public abstract void updateCurrencyForUser(String uuid, int i);

    public abstract void removeEvent(String id_document);

    public abstract void updateEvent(String id_document, Event event, FireStoreSql.SqlListener listener);

    public abstract void insertComment(Comment comment, FireStoreSql.SqlListener sqlListener);

    public abstract void getAllCommentsOfOneEvent(String event_id, SqlListenerComments listener);


    public interface SqlListener {
        void onCompleteListener();

        void onFailedListener(Exception e);
    }

    public interface SqlListenerEvents {
        void onGetAllEvents(List<Event> events);
    }

    public interface SqlListenerUsers {
        void onGetAllUsers(List<User> users);
    }

    public interface SqlListenerComments {
        void onGetCommentsFromEvent(List<Comment> users);
    }

    protected Context mContext;

    public abstract void insertEvent(String createdBy, Event event, FireStoreSql.SqlListener sqlListener);

    public abstract void getAllEvents(SqlListenerEvents sqlListenerEvents);

    public abstract void createUser(User user);

    public SQLBase(Context context) {
        this.mContext = context;
    }


}
