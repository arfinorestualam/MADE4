package com.example.apkfin4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apkfin4.model.favorite.Favorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.apkfin4.db.DbContract.Columns.BACKDROPPATH;
import static com.example.apkfin4.db.DbContract.Columns.CATEGORY;
import static com.example.apkfin4.db.DbContract.Columns.FAVID;
import static com.example.apkfin4.db.DbContract.Columns.OVERVIEW;
import static com.example.apkfin4.db.DbContract.Columns.POSTERPATH;
import static com.example.apkfin4.db.DbContract.Columns.RELEASE_DATE;
import static com.example.apkfin4.db.DbContract.Columns.TITLE;
import static com.example.apkfin4.db.DbContract.Columns.TABLE_FAVORITE;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DbHelper dbHelper;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavHelper(Context context) {dbHelper = new DbHelper(context);}

    public static FavHelper getInstance(Context context) {
        if(INSTANCE == null) {synchronized (SQLiteOpenHelper.class){
            if(INSTANCE == null) {INSTANCE = new FavHelper(context);}
        }}
        return INSTANCE;
    }

    public void open() throws SQLException {database = dbHelper.getWritableDatabase();}

    public void close() {dbHelper.close();
    if (database.isOpen())
        database.close();
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE,null,_ID + " = ?"
        ,new String[]{id}, null, null, null, null);}

    public Cursor query() {
        return database.query(DATABASE_TABLE, null,null,null,null,null,_ID + " DESC");}

    public long insert(ContentValues contentValues) {return database.insert(DATABASE_TABLE,null,contentValues); }

    public int update(String id, ContentValues contentValues) {return database.update(DATABASE_TABLE,contentValues,_ID + " = ?", new String[] {id});}

    public int delete(String id) {return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});}

    public static ArrayList<Favorite> getFilmFavorite(Cursor cursor) {
        ArrayList<Favorite> arrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            int favId = cursor.getInt(cursor.getColumnIndexOrThrow(FAVID));
            String backdrop = cursor.getString(cursor.getColumnIndexOrThrow(BACKDROPPATH));
            String posterpath = cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String release = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));
            if(category.equals("movie")) {
                arrayList.add(new Favorite(id, favId, title, backdrop, posterpath, overview, release, category));
            }}

        return arrayList;
        }

        public static  ArrayList<Favorite> getTvFavorite(Cursor cursor) {
            ArrayList<Favorite> arrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                int favId = cursor.getInt(cursor.getColumnIndexOrThrow(FAVID));
                String backdrop = cursor.getString(cursor.getColumnIndexOrThrow(BACKDROPPATH));
                String posterpath = cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
                String release = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY));
                if(category.equals("tv")) {
                    arrayList.add(new Favorite(id, favId, title, backdrop, posterpath, overview, release, category));
                }}

            return arrayList;
        }



    }



