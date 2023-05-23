package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // db name
    public static final String DB_NAME = "gamebud_db";

    // table USER
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "userName";
    public static final String USER_REGION = "region";
    public static final String USER_SERVER = "server";
    public static final String USER_RANK_PEAK = "peakRank";
    public static final String USER_RANK_NOW = "currentRank";
    public static final String USER_TWT = "contactTwt";
    public static final String USER_DISC = "contactDisc";
    public static final String USER_FB = "contactFb";

    // table ACCOUNT
    public static final String ACC_TABLE_NAME = "accounts";
    public static final String ACC_ID = "accID";
    public static final String ACC_USERNAME = "username";
    public static final String ACC_PASSWORD = "password";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // creating TABLES
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT,"
                + USER_REGION + " TEXT,"
                + USER_SERVER + " TEXT,"
                + USER_RANK_PEAK + " TEXT,"
                + USER_RANK_NOW + " TEXT,"
                + USER_TWT + " TEXT,"
                + USER_DISC + " TEXT,"
                + USER_FB + " TEXT,"
                + ACC_ID + " INTEGER,"
                + " CONSTRAINT fk_user_account FOREIGN KEY ("
                + ACC_ID + ") REFERENCES "
                + ACC_TABLE_NAME + "("
                + ACC_ID + "))";

        db.execSQL(query);

        query = "CREATE TABLE " + ACC_TABLE_NAME + " ("
                + ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACC_USERNAME + " TEXT,"
                + ACC_PASSWORD + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACC_TABLE_NAME);
        onCreate(db);
    }

    // add a new user to our sqlite database.
    public boolean insertUser (String userName, String region, String server, String peakRank, String currentRank, String contactTwt, String contactDisc, String contactFb) {
        // calling writable method into our database as we are writing data into it
        SQLiteDatabase db = this.getWritableDatabase();

        // retrieve max ID, or our new user
        Cursor cursor = db.rawQuery("Select MAX(userID) from users",null);
        cursor.moveToNext();
        int accID = cursor.getInt(0);

        // creating a variable for our column values
        ContentValues contentValues = new ContentValues();

        // passing all values along
        contentValues.put("userName", userName);
        contentValues.put("region", region);
        contentValues.put("server", server);
        contentValues.put("peakRank", peakRank);
        contentValues.put("currentRank", currentRank);
        contentValues.put("contactTwt", contactTwt);
        contentValues.put("contactDisc", contactDisc);
        contentValues.put("contactFb", contactFb);
        contentValues.put("accID", accID);

        // passing values to our table
        db.insert("users", null, contentValues);

        // closing db after insertion
        db.close();

        return true;
    }

    // add a new account to our sqlite database.
    public boolean insertAcc (String username, String password) {
        // calling writable method into our database as we are writing data into it
        SQLiteDatabase db = this.getWritableDatabase();

        // creating a variable for our column values
        ContentValues contentValues = new ContentValues();

        // passing all values along
        contentValues.put("username", username);
        contentValues.put("password", password);

        // passing values to our table
        db.insert("accounts", null, contentValues);

        // closing db after insertion
        db.close();

        return true;
    }

    public Cursor getAccData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from accounts ",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getUserData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM users LEFT JOIN accounts ON accounts.accID = users.accID where accounts.username = name;",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

}
