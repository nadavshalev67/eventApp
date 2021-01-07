package com.example.eventapp.database;

import android.content.Context;

import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;

import java.util.List;

public abstract class SQLBase {
    protected String EVENT_TABLE_NAME = "event";
    protected String USER_TABLE_NAME = "Users";

    public abstract void updateApprovedUser(String id_document, List<String> approvedUser);

    public abstract void updateRejectedUser(String id_document, List<String> rejectedUser);


    public interface SqlListener {
        void onCompleteListener();

        void onFailedListener(Exception e);
    }

    public interface SqlListenerEvents {
        void onGetAllEvents(List<Event> events);
    }

    protected Context mContext;

    public abstract void insertEvent(Event event, FireStoreSql.SqlListener sqlListener);

    public abstract void getAllEvents(SqlListenerEvents sqlListenerEvents);

    public abstract void createUser(User user);

    public SQLBase(Context context) {
        this.mContext = context;
    }


}
