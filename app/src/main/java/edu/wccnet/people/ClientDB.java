package edu.wccnet.people;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClientDB {
    private String TAG=MainActivity.class.toString();

    SQLiteOpenHelper helper;

    ClientDB(Context context) {
        helper = new PeopleHelper(context);
    }

    Cursor getPeople() {
        Cursor cursor = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            cursor = db.query(Constants.PEOPLE_TABLE, null, null, null, null, null, Constants.NAME);
        } catch (SQLException e) {
            Log.d(TAG, "Query error: " + e);
        }
        return cursor;
    }

    int delete(String name) {
        int num = 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String where_clause = Constants.NAME + "='" + name + "'";
            num = db.delete(Constants.PEOPLE_TABLE, where_clause, null);
        } catch (SQLException e) {
            Log.d(TAG, "Delete error: " + e);
        }
        return num;
    }

    void insert(String name, int age, double height) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.NAME, name);
            values.put(Constants.AGE, age);
            values.put(Constants.HEIGHT, height);
            db.insert( Constants.PEOPLE_TABLE, null, values );
        }
        catch( SQLException e ) {
           Log.d( TAG, "Insert error: " + e );
        }
    }
}

