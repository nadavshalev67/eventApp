package com.example.eventapp.database.sql_lite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EventDB1";

    // item table
    private static final String TABLE_EVENT_NAME = "event";
    private static final String  EVENT_COLUMNS_SERIALNUM= "serialNum";
    private static final String EVENT_COLUMN_EVENTTYPE= "eventType";
    private static final String EVENT_COLUMN_IMAGE = "image";
    private static final String EVENT_COLUMN_DESCRIPTION = "description";
    private static final String EVENT_COLUMN_ADDRESS = "address";
    private static final String EVENT_COLUMN_LEVELOFRISK = "levelofrisk";
    private static final String EVENT_COLUMN_EMAIL = "email";



    // item table
    private static final String TABLE_COMMENT_NAME = "comment";
    private static final String COMMENT_COLUMN_ID = "ID";
    private static final String COMMENT_COLUMN_SERIALNUM = "serialNum";
    private static final String COMMENT_COLUMN_CONTENT = "content";
    private static final String COMMENT_COLUMN_USEREMAIL = "userEmail";

    private static final String[] TABLE_EVENT_COLUMNS = {EVENT_COLUMNS_SERIALNUM,EVENT_COLUMN_EVENTTYPE,
            EVENT_COLUMN_IMAGE ,EVENT_COLUMN_DESCRIPTION,EVENT_COLUMN_ADDRESS,EVENT_COLUMN_LEVELOFRISK,
            EVENT_COLUMN_EMAIL};



    private static final String[] TABLE_COMMENT_COLUMNS = {COMMENT_COLUMN_ID, COMMENT_COLUMN_SERIALNUM,
            COMMENT_COLUMN_CONTENT, COMMENT_COLUMN_USEREMAIL  };

    private SQLiteDatabase db = null;


    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public SQLiteDatabase getDb() {
        return db;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            // SQL statement to create item table
            String CREATE_ITEM_TABLE = "create table if not exists "
                    + TABLE_EVENT_NAME +" ( "
                    + EVENT_COLUMNS_SERIALNUM +" TEXT PRIMARY KEY, "
                    + EVENT_COLUMN_EVENTTYPE  +" TEXT, "
                    + EVENT_COLUMN_IMAGE  + " TEXT, "
                    + EVENT_COLUMN_DESCRIPTION + " TEXT, "
                    + EVENT_COLUMN_ADDRESS + " TEXT, "
                    + EVENT_COLUMN_LEVELOFRISK + " TEXT, "
                    + EVENT_COLUMN_EMAIL + " TEXT) ";
            db.execSQL(CREATE_ITEM_TABLE);

            // SQL statement to create item table
            String CREATE_COMMENT_TABLE = "create table if not exists " + TABLE_COMMENT_NAME +" ( "
                    + COMMENT_COLUMN_ID +" TEXT PRIMARY KEY, "
                    + COMMENT_COLUMN_SERIALNUM +" TEXT , " // ??FOREIGN KEY
                    + COMMENT_COLUMN_CONTENT +" TEXT, "
                    + COMMENT_COLUMN_USEREMAIL +" TEXT) ";
            db.execSQL(CREATE_COMMENT_TABLE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENT_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COMMENT_NAME);
        onCreate(db);

    }
//
//    public void addEvent(Event item){
//
////        try {
////            // make values to be inserted
////            ContentValues values = new ContentValues();
////
////            values.put(EVENT_COLUMNS_SERIALNUM, item.getID());
////            values.put(EVENT_COLUMN_EVENTTYPE, item.getEventType());
////            values.put(EVENT_COLUMN_IMAGE, item.getPic());
////            values.put(EVENT_COLUMN_DESCRIPTION, item.getDescription());
////            values.put(EVENT_COLUMN_ADDRESS, item.getAddress());
////            values.put(EVENT_COLUMN_LEVELOFRISK, item.getLevelofrisk());
////            values.put(EVENT_COLUMN_EMAIL, item.getOwnerEmail());
////
////            //insert item
////            db.insert(TABLE_EVENT_NAME, null, values);
////        }catch(Throwable t) {
////            t.printStackTrace();
////        }
//    }
//
//    public Event readEvent(String id){
//        Event item = null ;
//        Cursor cursor = null;
//        try {
//            // get  query
//           /* cursor = db
//                    .query(TABLE_EVENT_NAME,
//                            TABLE_EVENT_COLUMNS,
//                            EVENT_COLUMNS_SERIALNUM+ " = ?",
//                            new String[] { id },
//                            null, null,
//                            null, null); */
//            //if results !=null , parse the first one
//            if (cursor != null && cursor.getCount() > 0) {
//
//                cursor.moveToFirst();
//                item = cursorToItemEvent(cursor);
//            }
//        }catch (Throwable t) {
//            t.printStackTrace();
//        }
//        finally{
//            if (cursor != null){
//                cursor.close();
//            }
//        }
//         return item;
//
//        }
//
//
//
//    public List<Event> getAllEvents() {
//        List<Event> result = new ArrayList<Event>();
//        Cursor cursor = null;
//        try {
//
//            String query = "SELECT * FROM " + TABLE_EVENT_NAME;
//            //Cursor point to a location in results
//            cursor = db.rawQuery(query, null);
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                ;
//                Event item = cursorToItemEvent(cursor);
//                result.add(item);
//                cursor.moveToNext();
//            }
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        finally {
//            //make sure to close the cursor
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return result;
//    }
//    private Event cursorToItemEvent(Cursor cursor){
//        Event result = new Event();
//        try {
////            result = new Event();
////            result.setID(cursor.getString(0));
////            result.setEventType(cursor.getString(1));
////            result.setPic(cursor.getString(2));
////            result.setDescription(cursor.getString(3));
////            result.setAddress(cursor.getString(4));
////            result.setLevelofrisk(cursor.getString(5));
////            result.setOwnerEmail(cursor.getString(6));
//
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public int updateEvent(Event item){
//        int count = 0 ;
//        try {
//
//            //make values to be inserted
//            ContentValues values = new ContentValues();
//            values.put(EVENT_COLUMNS_SERIALNUM, item.getID());
//            values.put(EVENT_COLUMN_EVENTTYPE, item.getEventType());
//            values.put(EVENT_COLUMN_IMAGE, item.getPic());
//            values.put(EVENT_COLUMN_ADDRESS, item.getDescription());
//            values.put(EVENT_COLUMN_ADDRESS, item.getAddress());
//            values.put(EVENT_COLUMN_LEVELOFRISK, item.getLevelofrisk());
//            values.put(EVENT_COLUMN_EMAIL, item.getOwnerEmail());
//
//            //update
//            // the update commend take 3 arguments (table name, values that we inserted , primery key to be identify)
//            count = db.update(TABLE_EVENT_NAME, values, EVENT_COLUMNS_SERIALNUM + " = ?",
//                    new String[]{String.valueOf(item.getID())});
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//
//        return count;
//
//    }
//
//    public void deleteEvent(Event item){
//        try{
//
//            //delete event item
//            // the delete commend take 2 arguments (table name,  primery key to be identify as deleted by ID)
//            db.delete(TABLE_EVENT_NAME,EVENT_COLUMNS_SERIALNUM +" = ?",
//                     new String[]{item.getID() });
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//
//    }
//
//
//    public void addComment(Comment item){
//        try{
//            //make values to be inserted
//            ContentValues values = new ContentValues();
//
//            values.put(COMMENT_COLUMN_ID, item.getCommentID());
//            values.put(COMMENT_COLUMN_SERIALNUM, item.getEventNum());
//            values.put(COMMENT_COLUMN_CONTENT, item.getContent());
//            values.put(COMMENT_COLUMN_USEREMAIL, item.getCurrentEmail());
//
//            //insert item
//            db.insert(TABLE_COMMENT_NAME,null,values);
//
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//    }
//
//    public Comment readComment(String id){
//        Comment item = null ;
//        Cursor cursor =null;
//        try{
//            // get  query
//           /* cursor = db
//                    .query(TABLE_EVENT_NAME,
//                           TABLE_COMMENT_COLUMNS,
//                            EVENT_COLUMNS_SERIALNUM + " = ?",
//                            new String[] { id },
//                            null, null,
//                            null, null); */
//
//            //if results !=null parse the first one
//            if(cursor != null && cursor.getCount() >0){
//
//                cursor.moveToFirst();
//                item = cursorToItemComment(cursor);
//            }
//
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        finally{
//            if(cursor!=null){
//                cursor.close();
//            }
//        }
//        return item;
//    }
//
//    public List<Comment> getAllComments() {
//        List<Comment> result = new ArrayList<Comment>();
//        Cursor cursor = null;
//        try{
//            /*cursor = db.query(TABLE_EVENT_NAME, TABLE_COMMENT_COLUMNS, null, null,
//                    null, null, null); */
//            //query
//            String query = "SELECT * FROM " + TABLE_COMMENT_NAME;
//            //Cursor point to Location in your results
//            cursor = db.rawQuery(query,null);
//
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                Comment item = cursorToItemComment(cursor);
//                result.add(item);
//                cursor.moveToNext();
//            }
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        finally {
//            //make sure to close the cursor
//            if(cursor!=null){
//                cursor.close();
//            }
//        }
//        return result;
//
//    }
//
//    private Comment cursorToItemComment(Cursor cursor){
//        Comment result = new Comment();
//        try{
//            result = new Comment();
//            result.setCommentID(cursor.getString(0));
//            result.setEventNum(cursor.getString(1));
//            result.setContent(cursor.getString(2));
//            result.setCurrentEmail(cursor.getString(3));
//
//
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        return result;
//    }
//
//    public int updateComment(Comment item){
//        int count = 0 ;
//        try {
//
//            //make values to be insrted
//            ContentValues values = new ContentValues();
//            //make values to be inserted
//            values.put(COMMENT_COLUMN_ID, item.getCommentID());
//            values.put(COMMENT_COLUMN_SERIALNUM, item.getEventNum());
//            values.put(COMMENT_COLUMN_CONTENT, item.getContent());
//            values.put(COMMENT_COLUMN_USEREMAIL, item.getCurrentEmail());
//
//
//            //update
//            /* the update commend take 3 arguments
//             (table name, values that we inserted , primery key to be identify)*/
//            count = db.update(TABLE_COMMENT_NAME, values, COMMENT_COLUMN_ID + " = ?",
//                    new String[]{String.valueOf(item.getCommentID())});
//
//
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        return count;
//    }
//
//    public void deleteComment(Comment item){
//        try{
//            //delete event item
//            /* the delete commend take 2 arguments
//            (table name,  primery key to be identify as deleted by ID)*/
//            db.delete(TABLE_COMMENT_NAME, COMMENT_COLUMN_ID+ " = ?",
//                    new String[] { item.getCommentID() });
//
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
//    }
//
//
//    public void open() {
//        try {
//            db = getWritableDatabase();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }
//
//    public void close() {
//        try {
//            db.close();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }

}
