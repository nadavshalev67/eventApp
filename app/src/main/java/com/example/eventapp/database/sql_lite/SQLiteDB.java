package com.example.eventapp.database.sql_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eventapp.models.Comment;
import com.example.eventapp.models.Event;
import com.example.eventapp.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private static final String EVENT_COLUMN_APPROVEDLIST = "approved_list";
    private static final String EVENT_COLUMN_REJCTEDLIST = "rejected_list";
    private static final String EVENT_COLUMN_USERID = "user_id";



    // item table
    private static final String TABLE_COMMENT_NAME = "comment";
    private static final String COMMENT_COLUMN_ID = "ID";
    private static final String COMMENT_COLUMN_EVENTIDRELATED = "eventidrelated";
    private static final String COMMENT_COLUMN_CONTENT = "content";
    private static final String COMMENT_COLUMN_USEREMAIL = "userEmail";

    //item table
    private static final String TABLE_USERS_NAME = "users";
    private static final String USERS_COLUMN_UUID = "uuid";
    private static final String USERS_COLUMN_EMAIL = "email ";
    private static final String USERS_COLUMN_AMOUNTMYEVENTS = "amount_my_events";
    private static final String USERS_COLUMN_AMOUNTREJECTBYME = "amount_rejected_by_me";
    private static final String USERS_COLUMN_AMOUNTAPPROVEBYME = "amount_approved_by_me";
    private static final String USERS_COLUMN_COINSFROMMETOOTHER = "conis_from_my_approve_to_other_user";
    private static final String USERS_COLUMN_COINSFROMOTHERTOME = "coins_aprroved_by_other_users_to_my_events";

    private static final String[] TABLE_EVENT_COLUMNS = {EVENT_COLUMNS_SERIALNUM,EVENT_COLUMN_EVENTTYPE,
            EVENT_COLUMN_IMAGE ,EVENT_COLUMN_DESCRIPTION,EVENT_COLUMN_ADDRESS,EVENT_COLUMN_LEVELOFRISK,
            EVENT_COLUMN_APPROVEDLIST,EVENT_COLUMN_REJCTEDLIST,EVENT_COLUMN_USERID };



    private static final String[] TABLE_COMMENT_COLUMNS = {COMMENT_COLUMN_ID, COMMENT_COLUMN_EVENTIDRELATED,
            COMMENT_COLUMN_CONTENT, COMMENT_COLUMN_USEREMAIL  };

    private static final String[] TABLE_USERS_COLUMNS = {USERS_COLUMN_UUID,USERS_COLUMN_EMAIL
            ,USERS_COLUMN_AMOUNTMYEVENTS,USERS_COLUMN_AMOUNTREJECTBYME,USERS_COLUMN_AMOUNTAPPROVEBYME
            ,USERS_COLUMN_COINSFROMMETOOTHER,USERS_COLUMN_COINSFROMOTHERTOME};

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
                    + EVENT_COLUMN_APPROVEDLIST+ " TEXT, "
                    + EVENT_COLUMN_REJCTEDLIST+ " TEXT, "
                    +EVENT_COLUMN_USERID + " TEXT)";
            db.execSQL(CREATE_ITEM_TABLE);

            // SQL statement to create item table
            String CREATE_COMMENT_TABLE = "create table if not exists " + TABLE_COMMENT_NAME +" ( "
                    + COMMENT_COLUMN_ID +" TEXT PRIMARY KEY, "
                    + COMMENT_COLUMN_EVENTIDRELATED +" TEXT , " // ??FOREIGN KEY
                    + COMMENT_COLUMN_CONTENT +" TEXT, "
                    + COMMENT_COLUMN_USEREMAIL +" TEXT) ";
            db.execSQL(CREATE_COMMENT_TABLE);

            //SQL statement to create item table
            String CREATE_USERS_TABLE = "create table if not exists " + TABLE_USERS_NAME + " ( "
                    + USERS_COLUMN_UUID +" TEXT PRIMARY KEY, "
                    + USERS_COLUMN_EMAIL +" TEXT , "
                    + USERS_COLUMN_AMOUNTMYEVENTS +" TEXT , "
                    + USERS_COLUMN_AMOUNTREJECTBYME +" TEXT , "
                    + USERS_COLUMN_AMOUNTAPPROVEBYME +" TEXT , "
                    + USERS_COLUMN_COINSFROMMETOOTHER +" TEXT, "
                    +USERS_COLUMN_COINSFROMOTHERTOME +" TEXT) ";
            db.execSQL(CREATE_USERS_TABLE);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENT_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COMMENT_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS_NAME);
        onCreate(db);

    }

    public void addEvent(Event item){

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();

            values.put(EVENT_COLUMNS_SERIALNUM, item.id_document);
            values.put(EVENT_COLUMN_USERID, item.user_id);
            values.put(EVENT_COLUMN_EVENTTYPE, item.event_name);
            values.put(EVENT_COLUMN_IMAGE, item.url_of_pitcure);
            values.put(EVENT_COLUMN_DESCRIPTION, item.event_description);
            values.put(EVENT_COLUMN_ADDRESS, item.event_adress);
            values.put(EVENT_COLUMN_LEVELOFRISK, item.level_of_risk);
            values.put(EVENT_COLUMN_APPROVEDLIST, String.valueOf(item.approved_users_list));
            values.put(EVENT_COLUMN_REJCTEDLIST, String.valueOf(item.rejected_users_list));


            //insert item
            db.insert(TABLE_EVENT_NAME, null, values);
        }catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public Event readEvent(String id){
        Event item = null ;
        Cursor cursor = null;
        try {
            // get  query
//           /* cursor = db
//                    .query(TABLE_EVENT_NAME,
//                            TABLE_EVENT_COLUMNS,
//                            EVENT_COLUMNS_SERIALNUM+ " = ?",
//                            new String[] { id },
//                            null, null,
//                            null, null); */


            // if results !=null , parse the first one
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                item = cursorToItemEvent(cursor);
            }
        }catch (Throwable t) {
            t.printStackTrace();
        }
        finally{
            if (cursor != null){
                cursor.close();
            }
        }
        return item;

    }



    public List<Event> getAllEvents() {
        List<Event> result = new ArrayList<Event>();
        Cursor cursor = null;
        try {

            String query = "SELECT * FROM " + TABLE_EVENT_NAME;
            //Cursor point to a location in results
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ;
                Event item = cursorToItemEvent(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
        finally {
            //make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    private Event cursorToItemEvent(Cursor cursor){
        Event result = new Event();
        try {
            result = new Event();
            result.setId_document(cursor.getString(0));
            result.setEvent_name(cursor.getString(1));
            result.setUrl_of_pitcure(cursor.getString(2));
            result.setEvent_description(cursor.getString(3));
            result.setEvent_adress(cursor.getString(4));
            result.setLevel_of_risk(cursor.getString(5));
            result.setApproved_users_list(Collections.singletonList(cursor.getString(6)));
            result.setRejected_users_list(Collections.singletonList(cursor.getString(7)));

        }catch (Throwable t){
            t.printStackTrace();
        }

        return result;
    }

    public int updateEvent(Event item){
        int count = 0 ;
        try {

            //make values to be inserted
            ContentValues values = new ContentValues();
            values.put(EVENT_COLUMNS_SERIALNUM, item.getId_document());
            values.put(EVENT_COLUMN_EVENTTYPE, item.getEvent_name());
            values.put(EVENT_COLUMN_IMAGE, item.getUrl_of_pitcure());
            values.put(EVENT_COLUMN_ADDRESS, item.getEvent_description());
            values.put(EVENT_COLUMN_ADDRESS, item.getEvent_adress());
            values.put(EVENT_COLUMN_LEVELOFRISK, item.getLevel_of_risk());
            values.put(EVENT_COLUMN_APPROVEDLIST, String.valueOf(item.getApproved_users_list()));
            values.put(EVENT_COLUMN_REJCTEDLIST, String.valueOf(item.getRejected_users_list()));
            values.put(EVENT_COLUMN_USERID, item.getUser_id());

            //update
            // the update commend take 3 arguments (table name, values that we inserted , primery key to be identify)
            count = db.update(TABLE_EVENT_NAME, values, EVENT_COLUMNS_SERIALNUM + " = ?",
                    new String[]{String.valueOf(item.getId_document())});
        }catch (Throwable t){
            t.printStackTrace();
        }

        return count;

    }

    public void deleteEvent(Event item){
        try{

            //delete event item
            // the delete commend take 2 arguments (table name,  primery key to be identify as deleted by ID)
            db.delete(TABLE_EVENT_NAME,EVENT_COLUMNS_SERIALNUM +" = ?",
                    new String[]{item.getId_document() });
        }catch(Throwable t){
            t.printStackTrace();
        }

    }


    public void addComment(Comment item){
        try{
            //make values to be inserted
            ContentValues values = new ContentValues();

            values.put(COMMENT_COLUMN_ID, item.getCommentID());
            values.put(COMMENT_COLUMN_EVENTIDRELATED, item.getEventIdrelated());
            values.put(COMMENT_COLUMN_CONTENT, item.getContent());
            values.put(COMMENT_COLUMN_USEREMAIL, item.getEmail());

            //insert item
            db.insert(TABLE_COMMENT_NAME,null,values);

        }catch(Throwable t){
            t.printStackTrace();
        }
    }

    public Comment readComment(String id){
        Comment item = null ;
        Cursor cursor =null;
        try{
            // get  query
//           /* cursor = db
//                    .query(TABLE_EVENT_NAME,
//                           TABLE_COMMENT_COLUMNS,
//                            EVENT_COLUMNS_SERIALNUM + " = ?",
//                            new String[] { id },
//                            null, null,
//                            null, null); */
//
//            //if results !=null parse the first one
            if(cursor != null && cursor.getCount() >0){

                cursor.moveToFirst();
                item = cursorToItemComment(cursor);
            }

        }catch(Throwable t){
            t.printStackTrace();
        }
        finally{
            if(cursor!=null){
                cursor.close();
            }
        }
        return item;
    }

    public List<Comment> getAllComments() {
        List<Comment> result = new ArrayList<Comment>();
        Cursor cursor = null;
        try{
//            /*cursor = db.query(TABLE_EVENT_NAME, TABLE_COMMENT_COLUMNS, null, null,
//                    null, null, null); */
            //query
            String query = "SELECT * FROM " + TABLE_COMMENT_NAME;
            //Cursor point to Location in your results
            cursor = db.rawQuery(query,null);

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Comment item = cursorToItemComment(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
        finally {
            //make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;

    }

    private Comment cursorToItemComment(Cursor cursor){
        Comment result = new Comment();
        try{
            result = new Comment();
            result.setCommentID(cursor.getString(0));
            result.setEventIdrelated(cursor.getString(1));
            result.setContent(cursor.getString(2));
            result.setEmail(cursor.getString(3));


        }catch(Throwable t){
            t.printStackTrace();
        }
        return result;
    }

    public int updateComment(Comment item){
        int count = 0 ;
        try {

            //make values to be insrted
            ContentValues values = new ContentValues();
            //make values to be inserted
            values.put(COMMENT_COLUMN_ID, item.getCommentID());
            values.put(COMMENT_COLUMN_EVENTIDRELATED, item.eventIdrelated);
            values.put(COMMENT_COLUMN_CONTENT, item.getContent());
            values.put(COMMENT_COLUMN_USEREMAIL, item.getEmail());


            //update
            /* the update commend take 3 arguments
            (table name, values that we inserted , primery key to be identify)*/
            count = db.update(TABLE_COMMENT_NAME, values, COMMENT_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(item.getCommentID())});


        }catch(Throwable t){
            t.printStackTrace();
        }
        return count;
    }
    public void deleteComment(Comment item){
        try{
            //delete event item
            /* the delete commend take 2 arguments
           (table name,  primery key to be identify as deleted by ID)*/
            db.delete(TABLE_COMMENT_NAME, COMMENT_COLUMN_ID+ " = ?",
                    new String[] { item.getCommentID() });

        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public void addUser(User item){

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();

            values.put(USERS_COLUMN_UUID, item.getUuid());
            values.put(USERS_COLUMN_EMAIL, item.getEmail());
            values.put(USERS_COLUMN_AMOUNTMYEVENTS, item.getAmout_of_my_events());
            values.put(USERS_COLUMN_AMOUNTREJECTBYME, item.getAmount_rejected_by_me());
            values.put(USERS_COLUMN_AMOUNTAPPROVEBYME, item.getAmount_approved_by_me());
            values.put(USERS_COLUMN_COINSFROMMETOOTHER,item.getConis_from_my_approve_to_other_user());
            values.put(USERS_COLUMN_COINSFROMOTHERTOME,item.getCoins_aprroved_by_other_users_to_my_events());

            //insert item
            db.insert(TABLE_USERS_NAME, null, values);
        }catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public User readUser(String id){
        User item = null ;
        Cursor cursor = null;
        try {
            // get  query
//           /* cursor = db
            //                   .query(TABLE_USERS_NAME,
//                            USERS_COLUMN_UUID+ " = ?",
//                            new String[] { id },
//                            null, null,
//                            null, null); */


            // if results !=null , parse the first one
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                item = cursorToItemUser(cursor);
            }
        }catch (Throwable t) {
            t.printStackTrace();
        }
        finally{
            if (cursor != null){
                cursor.close();
            }
        }
        return item;

    }



    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        Cursor cursor = null;
        try {

            String query = "SELECT * FROM " + TABLE_USERS_NAME;
            //Cursor point to a location in results
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ;
                User item = cursorToItemUser(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
        finally {
            //make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    private User cursorToItemUser(Cursor cursor){
        User result = new User();
        try {
            result = new User();
            result.setUuid(cursor.getString(0));
            result.setEmail(cursor.getString(1));
            result.setAmout_of_my_events(cursor.getString(2));
            result.setAmount_rejected_by_me(cursor.getString(3));
            result.setAmount_approved_by_me(cursor.getString(4));
            result.setConis_from_my_approve_to_other_user(cursor.getString(5));
            result.setCoins_aprroved_by_other_users_to_my_events(cursor.getString(6));

        }catch (Throwable t){
            t.printStackTrace();
        }

        return result;
    }

    public int updateUser(User item){
        int count = 0 ;
        try {

            //make values to be inserted
            ContentValues values = new ContentValues();
            values.put(USERS_COLUMN_UUID, item.getUuid());
            values.put(USERS_COLUMN_EMAIL, item.getEmail());
            values.put(USERS_COLUMN_AMOUNTMYEVENTS, item.getAmout_of_my_events());
            values.put(USERS_COLUMN_AMOUNTREJECTBYME, item.getAmount_rejected_by_me());
            values.put(USERS_COLUMN_AMOUNTAPPROVEBYME, item.getAmount_approved_by_me());
            values.put(USERS_COLUMN_COINSFROMMETOOTHER, item.getConis_from_my_approve_to_other_user());
            values.put(USERS_COLUMN_COINSFROMOTHERTOME, String.valueOf(item.getCoins_aprroved_by_other_users_to_my_events()));


            //update
            // the update commend take 3 arguments (table name, values that we inserted , primery key to be identify)
            count = db.update(TABLE_USERS_NAME, values, USERS_COLUMN_UUID + " = ?",
                    new String[]{String.valueOf(item.getUuid())});
        }catch (Throwable t){
            t.printStackTrace();
        }

        return count;

    }

    public void deleteUser(User item){
        try{

            //delete User item
            // the delete commend take 2 arguments (table name,  primery key to be identify as deleted by ID)
            db.delete(TABLE_USERS_NAME,USERS_COLUMN_UUID +" = ?",
                    new String[]{item.getUuid() });
        }catch(Throwable t){
            t.printStackTrace();
        }

    }







    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
