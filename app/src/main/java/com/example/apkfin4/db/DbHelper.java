package com.example.apkfin4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.apkfin4.db.DbContract.Columns.TABLE_FAVORITE;

public class DbHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VER = 1;
    private static final String SQL_CREATE_FAVORITE = String.format(
            "CREATE TABLE %s" + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL)",

            TABLE_FAVORITE,
            DbContract.Columns._ID,
            DbContract.Columns.FAVID,
            DbContract.Columns.BACKDROPPATH,
            DbContract.Columns.POSTERPATH,
            DbContract.Columns.TITLE,
            DbContract.Columns.RELEASE_DATE,
            DbContract.Columns.OVERVIEW,
            DbContract.Columns.CATEGORY

    );

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(sqLiteDatabase);
    }
}
