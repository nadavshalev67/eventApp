package com.example.eventapp.database;

import android.content.Context;

import com.example.eventapp.database.firestore.FireStoreSql;
import com.example.eventapp.models.Event;

import java.util.List;

public abstract class SQLBase {
    protected String EVENT_TABLE_NAME = "event";

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

    public SQLBase(Context context) {
        this.mContext = context;
    }

}
