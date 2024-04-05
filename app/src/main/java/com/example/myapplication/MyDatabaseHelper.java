package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, nome TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long inserirUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getUsername());
        values.put("password", user.getPassword());
        values.put("nome", user.getNome());
        return db.insert("users", null, values);
    }

    public boolean existeUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { "email" };
        String selection = "email = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean verificarLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { "email", "password" };
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean loginSuccess = cursor.moveToFirst();
        cursor.close();
        return loginSuccess;
    }


}
