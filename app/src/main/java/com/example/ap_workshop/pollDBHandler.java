package com.example.ap_workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class pollDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "pollDB";

    private static final int DB_VERSION = 13;

    private static final String TABLE_NAME = "polls";

    private static final String ID_COL = "id";

    private static final String QUESTION_COL = "question";

    private static final String DATECREATED_COL = "dateCreated";

    private static final String DATETO_COL = "dateTo";

    public pollDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTION_COL + " TEXT,"
                + DATECREATED_COL + " DATETIME,"
                + DATETO_COL + " DATETIME)";

        db.execSQL(query);
    }

    public void addNewPoll(String question, String dateCreated, String dateTo) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(QUESTION_COL, question);
        values.put(DATECREATED_COL, dateCreated);
        values.put(DATETO_COL, dateTo);

        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
