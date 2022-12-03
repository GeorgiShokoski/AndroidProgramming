package com.example.ap_workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "pollDB";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "registeredUsers";

    private static final String ID_COL = "id";

    private static final String FIRSTNAME_COL = "firstname";

    private static final String LASTNAME_COL = "lastname";

    private static final String EMAIL_COL = "email";

    private static final String PASSWORD_COL = "password";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRSTNAME_COL + " TEXT,"
                + LASTNAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewUser(String FirstName, String LastName, String Email, String Password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FIRSTNAME_COL, FirstName);
        values.put(LASTNAME_COL, LastName);
        values.put(EMAIL_COL, Email);
        values.put(PASSWORD_COL, Password);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
