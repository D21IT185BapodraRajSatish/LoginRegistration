package com.example.rajprc2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {


    private static final String DB_NAME = "user_db";
    private static final String TABLE_NAME = "user_detail";
    private static final int DB_VERSION = 1;

    private static final String ID_COL = "ID";
    private static final String Name_COL = "Name";
    private static final String Password_COL = "Password";
    private static final String TimeStamp_COL = "TimeStamp";


    public DBHandler(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        try {
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Name_COL + " TEXT,"
                    + Password_COL + " TEXT,"
                    + TimeStamp_COL + " TEXT )";


            sqLiteDatabase.execSQL(query);
        }catch (Exception e){
            Log.d("------------------------------>",e.toString());
        }


    }
    public void addNewUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues =  new ContentValues();




        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")  final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf3.format(timestamp);

        contentValues.put(Name_COL,user.getName());
        contentValues.put(Password_COL,SecurityMD5.getMd5Hash(user.getPassword()));
        contentValues.put(TimeStamp_COL,sdf3.format(timestamp));


        db.insert(TABLE_NAME,null,contentValues);
    }

    public UserModel fetchUserByID(String username){

        Cursor cursor = null;
        UserModel userModel= new UserModel();;
        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Name_COL + " = '" + username + "'";
            SQLiteDatabase db = this.getWritableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.getString(1).equals(username)){
                    cursor.moveToNext();
                }
                userModel.setName(cursor.getString(1));
                userModel.setPassword(cursor.getString(2));

            }

        }
        catch (Exception e){
            userModel.setName("null");
            userModel.setPassword("null");
        }
        return userModel;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
