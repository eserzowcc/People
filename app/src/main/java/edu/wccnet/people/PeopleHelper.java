package edu.wccnet.people;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PeopleHelper extends SQLiteOpenHelper {
    private String TAG=MainActivity.class.toString();

    private static final String Database_filename = "people.db";
    private static final int Database_version = 1;

    public PeopleHelper(Context context) {
        super(context, Database_filename, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d( TAG, "Creating table " + Constants.PEOPLE_TABLE );
        // create table people (name text, age integer, height real);
        String sql = "CREATE TABLE " + Constants.PEOPLE_TABLE + " (" + Constants.NAME + " text, " + Constants.AGE + " integer, " +
                Constants.HEIGHT + " real); ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d( TAG, "Dropping table " + Constants.PEOPLE_TABLE );
        db.execSQL("DROP TABLE " + Constants.PEOPLE_TABLE);
        onCreate(db);
    }
}