package com.example.eventapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {

    private static com.example.eventapp.DataBaseManager instance = null;
    private Context context = null;
    private static SQLiteDB db = null;
    private event selectedEvent = null;
    private Comment selectedComment = null;



    private DataBaseManager(){ }

    public static boolean checkdb(){
        return db==null ;
    }

    public static com.example.eventapp.DataBaseManager getInstance() {
        if (instance == null) {
            instance = new com.example.eventapp.DataBaseManager();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {
    }

    public Context getContext() {
        return context;

    }

    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new SQLiteDB(context);
            db.open();
        }
    }
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

    public void onUpgrade() {
        if (db != null ) {
            db.onUpgrade(db.getDb(), 1,1 );
        }
    }


    public void addEvent(event item) {
        if (db != null) {
            db.addEvent(item);
        }
    }

    public event readEvent(String id) {
        event result = null;
        if (db != null) {
            result = db.readEvent(id);
        }
        return result;
    }



    public List<event> getAllEvents() {
        List<event> result = new ArrayList<event>();
        if (db != null) {
            result = db.getAllEvents();
        }
        return result;
    }


    public void updateEvent(event item) {
        if (db != null && item != null) {
            db.updateEvent(item);
        }
    }


    public void deleteEvent(event item) {
        if (db != null) {
            db.deleteEvent(item);
        }
    }


    public event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }


    // Comment -----------------------------------------------------
    public void addComment(Comment item) {
        if (db != null) {
            db.addComment(item);
        }
    }

    public void deleteComment(Comment item) {
        if (db != null) {
            db.deleteComment(item);
        }
    }


}

