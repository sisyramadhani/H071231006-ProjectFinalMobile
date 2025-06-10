package com.projects.h071231006_projectfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "purepalate.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_INSTRUCTIONS = "instructions";
    public static final String COLUMN_IMAGE_URL = "image_url";

    private static final String CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_INGREDIENTS + " TEXT,"
            + COLUMN_INSTRUCTIONS + " TEXT,"
            + COLUMN_IMAGE_URL + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }
}