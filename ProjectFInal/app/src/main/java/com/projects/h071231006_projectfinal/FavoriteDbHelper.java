package com.projects.h071231006_projectfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper {
    private final DatabaseHelper dbHelper;

    public FavoriteDbHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addFavorite(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, recipe.getId());
        values.put(DatabaseHelper.COLUMN_TITLE, recipe.getTitle());
        values.put(DatabaseHelper.COLUMN_INGREDIENTS, recipe.getIngredients());
        values.put(DatabaseHelper.COLUMN_INSTRUCTIONS, recipe.getInstructions());
        values.put(DatabaseHelper.COLUMN_IMAGE_URL, recipe.getImageUrl());

        // Replace if already exists to avoid crash on duplicate ID
        db.insertWithOnConflict(DatabaseHelper.TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void removeFavorite(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_FAVORITES, DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }

    public List<Recipe> getAllFavorites() {
        List<Recipe> favorites = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_FAVORITES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_TITLE + " ASC"  // Sort alphabetically by title
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Recipe recipe = new Recipe();
                    recipe.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    recipe.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                    recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INGREDIENTS)));
                    recipe.setInstructions(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_INSTRUCTIONS)));
                    recipe.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_URL)));
                    favorites.add(recipe);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return favorites;
    }

    public boolean isFavorite(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_FAVORITES,
                null,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );

        boolean isFavorite = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isFavorite;
    }
}