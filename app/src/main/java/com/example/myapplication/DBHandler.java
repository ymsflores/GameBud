package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    // db name
    public static final String DB_NAME = "gamebud_db";
    public static final int dbVersion = 3;

    // table USER
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "displayName";
    public static final String USER_DESC = "userDesc";
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

    // table GAME
    public static final String GAME_TABLE_NAME = "games";
    public static final String GAME_ID = "gameID";
    public static final String GAME_NAME = "name";
    public static final String GAME_TIME = "time";
    public static final String GAME_CODE = "code";
    public static final String GAME_DETAILS = "details";

    // table RATING
    public static final String RATING_TABLE_NAME = "ratings";
    public static final String RATING_DESC = "description";
    public static final String RATING_VAL = "value";
    public static final String RATING_accSub = "accID_sub";
    public static final String RATING_accRcv = "accID_rcv";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // creating TABLES
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + USER_NAME + " TEXT, "
                + USER_DESC + " TEXT, "
                + USER_REGION + " TEXT,"
                + USER_SERVER + " TEXT,"
                + USER_RANK_PEAK + " TEXT,"
                + USER_RANK_NOW + " TEXT,"
                + USER_TWT + " TEXT,"
                + USER_FB + " TEXT,"
                + USER_DISC + " TEXT,"
                + ACC_ID + " INTEGER,"
                + " CONSTRAINT fk_user_account FOREIGN KEY ("
                + ACC_ID + ") REFERENCES "
                + ACC_TABLE_NAME + "("
                + ACC_ID + "))";

        db.execSQL(query);

        query = "CREATE TABLE " + ACC_TABLE_NAME + " ("
                + ACC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + ACC_USERNAME + " TEXT NOT NULL,"
                + ACC_PASSWORD + " TEXT NOT NULL)";

        db.execSQL(query);

        query = "CREATE TABLE " + GAME_TABLE_NAME + " ("
                + GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + GAME_NAME + " TEXT NOT NULL,"
                + GAME_TIME+ " TEXT NOT NULL,"
                + GAME_CODE + " TEXT NOT NULL,"
                + GAME_DETAILS + " TEXT NOT NULL,"
                + ACC_ID + " INTEGER,"
                + " CONSTRAINT fk_user_account FOREIGN KEY ("
                + ACC_ID + ") REFERENCES "
                + ACC_TABLE_NAME + "("
                + ACC_ID + "))";

        db.execSQL(query);

        query = "CREATE TABLE " + RATING_TABLE_NAME + " ("
                + RATING_DESC + " TEXT NOT NULL,"
                + RATING_VAL+ " FLOAT NOT NULL,"
                + RATING_accSub + " INTEGER NOT NULL,"
                + RATING_accRcv + " INTEGER NOT NULL,"
                + "PRIMARY KEY (" + RATING_accSub +"," + RATING_accRcv + "),"
                + " CONSTRAINT fk_accSub_account FOREIGN KEY ("
                + RATING_accSub + ") REFERENCES "
                + ACC_TABLE_NAME + "("
                + ACC_ID + ")," +
                " CONSTRAINT fk_accRcv_account FOREIGN KEY ("
                + RATING_accRcv + ") REFERENCES "
                + ACC_TABLE_NAME + "("
                + ACC_ID + "))";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACC_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GAME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE_NAME);
        onCreate(db);
    }

    // add a new user to our sqlite database.
    public boolean insertUser (String displayName, String userDesc, String region, String server, String peakRank, String currentRank, String contactTwt, String contactDisc, String contactFb, int accID) {
        // calling writable method into our database as we are writing data into it
        SQLiteDatabase db = this.getWritableDatabase();

        // creating a variable for our column values
        ContentValues contentValues = new ContentValues();

        // passing all values along
        contentValues.put("displayName", displayName);
        contentValues.put("userDesc", userDesc);
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

    public boolean insertGame(String name, String time, String code, String details, int accID) {
        // calling writable method into our database as we are writing data into it
        SQLiteDatabase db = this.getWritableDatabase();
        String table = GAME_TABLE_NAME;
        String whereClause = "accID=?";
        String[] whereArgs = new String[] { String.valueOf(accID) };

        Cursor cursor = gameCheck(accID);

        if (cursor.getCount() != 0) {
            db.delete(table, whereClause, whereArgs);
        }
        // creating a variable for our column values
        ContentValues contentValues = new ContentValues();

        // passing all values along
        contentValues.put("name", name);
        contentValues.put("time", time);
        contentValues.put("code", code);
        contentValues.put("details", details);
        contentValues.put("accID", accID);

        // passing values to our table
        db.insert("games", null, contentValues);

        // closing db after insertion
        db.close();

        return true;
    }

    public boolean insertRating(String description, float value, int accID_sub, int accID_rcv) {
        // calling writable method into our database as we are writing data into it
        SQLiteDatabase db = this.getWritableDatabase();

        // creating a variable for our column values
        ContentValues contentValues = new ContentValues();

        contentValues.put("description", description);
        contentValues.put("value", value);
        contentValues.put("accID_sub", accID_sub);
        contentValues.put("accID_rcv", accID_rcv);

        // check if rating already exists for user pair
        // if exists, update row. if not, insert new row.
        if (checkRating(accID_sub, accID_rcv)) {
            String[] args = { String.valueOf(accID_sub), String.valueOf(accID_rcv)};
            db.update(RATING_TABLE_NAME, contentValues, "accID_sub=? AND accID_rcv=?", args);
        } else {
            db.insert("ratings", null, contentValues);
        }



        // closing db after insertion
        db.close();

        return true;
    }

    public Cursor getAccData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from accounts ",null);
        if (cursor != null && cursor.moveToFirst()) {
            // here!
        }
        return cursor;
    }

    // This is a method to return ONE specific entry from the users table, connected to the logged in account
    public Cursor getUserData(int accIDInput){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from users where users.accID = " + accIDInput,null);
        if (cursor != null && cursor.moveToFirst()) {
            // here
        }
        return cursor;
    }

    // This is a method to return ALL entries from the users table
    public Cursor getUserData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from users",null);

        return cursor;
    }

    // This is a method to return ALL entries from the users table
    public Cursor getGameData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from games",null);
        if (cursor != null && cursor.moveToFirst()) {
            //here
        }
        return cursor;
    }

    public Cursor getGameData(int accID){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from games where accID = " + accID,null);
        //if (cursor != null && cursor.moveToFirst()) {
         //   //here
        //}
        return cursor;
    }

    public int getAccID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT accID from accounts where accounts.username = '" + name + "'", null);

        if (cursor != null && cursor.moveToFirst()) {
            // here
        }

        return cursor.getInt(0);
    }

    public float getRatings(int accID) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT avg(value) from ratings where accID_rcv = " + accID, null);

        if (cursor != null && cursor.moveToFirst()) {
        } else {
        }

        return cursor.getFloat(0);
    }


    public Cursor gameCheck(int accID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from games where accID = " + accID,null);

        if (cursor != null && cursor.moveToFirst()) {
            //here
        }

        return cursor;
    }
    // Check if user has existing review from user
    public boolean checkRating(int id1, int id2) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT value from ratings where accID_sub = " + id1 + " and accID_rcv =" + id2, null);

        if (cursor != null && cursor.moveToFirst()) {
            return true;
        }

        return false;
    }

    // Check if table was successfully created
    public void checkTable() {
        String table_name = "";

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()){
                if (cursor.getString(0).equals(table_name)) {
                }
            }

        }
    }

}
