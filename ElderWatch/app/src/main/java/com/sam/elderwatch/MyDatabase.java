package com.sam.elderwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SAM on 19/2/2016.
 */
public class MyDatabase extends SQLiteOpenHelper{

    private static final String DB = "myDB.db";
    private static final String TABLE = "contacts";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final int VERSION = 1;

    public MyDatabase(Context c){

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
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " + NUMBER + " VARCHAR(15))";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String[] tableName = {NAME};
        db.execSQL("DROP TABLE IF EXISTS ?", tableName);
        onCreate(db);
    }

    public void saveRecord(String name, String number){

        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(NUMBER, number);
        // more values to save!
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE, null, cv);
    }

    public void modifyRecord(String id, String name, String number){
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(NUMBER, number);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE, cv, "id=" + id, null);
    }

    public String[] findRecord(String id) {

        String clause = "id = ?";
        String[] args = {id};
        SQLiteDatabase db = this.getWritableDatabase();


        String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        Cursor cursor = db.query(TABLE, null, clause, args, null, null, null);
        if( cursor != null && cursor.moveToFirst() ){
            res[0] = cursor.getString(1);
            res[1] = cursor.getString(2);
            cursor.close();
        }
        return res;

    }

    public void deleteRecord(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String clause = "id=?";
        String[] args = {id};
        db.delete(TABLE, clause, args);
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
