package com.example.ap_workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class optionDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "pollDB";

    private static final int DB_VERSION = 11;

    private static final String TABLE_NAME = "pollOptions";

    private static final String ID_COL = "id";

    private static final String OPTION_COL = "optionName";

    private static final String POLLID_COL = "pollId";


    public optionDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OPTION_COL + " TEXT,"
                + POLLID_COL + " INTEGER)";

        db.execSQL(query);
    }

    public void addNewOption(String optionName, int pollId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OPTION_COL, optionName);

        values.put(POLLID_COL, pollId);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
