package com.sam.elderwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SAM on 21/2/2016.
 */
public class VideoDatabase extends SQLiteOpenHelper {

    private static final String DB = "videoDB.db";
    private static final String TABLE = "contacts";
    private static final String ID = "id";
    private static final String EVENT = "event";
    private static final String TIME = "time";
    private static final int VERSION = 1;

    public VideoDatabase(Context c){

        super(c, DB, null, VERSION);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " +
                TABLE +
                "(" + ID +
                " INTEGER PRIMARY KEY, " +
                EVENT + " TEXT, " + TIME + " VARCHAR(15))";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String[] tableName = {EVENT};
        db.execSQL("DROP TABLE IF EXISTS ?", tableName);
        onCreate(db);
    }

    public void saveRecord(String event, String time){

        ContentValues cv = new ContentValues();
        cv.put(EVENT, event);
        cv.put(TIME, time);
        // more values to save!
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE, null, cv);
    }

    public String[] findRecord(String id) {

        String clause = "id = ?";
        String[] args = {id};
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE, null, clause, args, null, null, null);
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";

        if( cursor != null && cursor.moveToFirst() ){
            res[0] = cursor.getString(1);
            res[1] = cursor.getString(2);
            cursor.close();
        }
        return res;


        //return result;
    }

    public int deleteRecord(String id) {

        SQLiteDatabase db = getWritableDatabase();
        String clause = "id = ?";
        String[] args = {id};
        return db.delete(TABLE, clause, args);
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}