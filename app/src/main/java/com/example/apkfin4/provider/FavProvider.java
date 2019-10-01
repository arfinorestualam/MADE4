package com.example.apkfin4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apkfin4.db.FavHelper;

import java.util.Objects;

import static com.example.apkfin4.db.DbContract.AUTHORITY;
import static com.example.apkfin4.db.DbContract.Columns.C_URI;
import static com.example.apkfin4.db.DbContract.Columns.TABLE_FAVORITE;

public class FavProvider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,TABLE_FAVORITE,FAVORITE);
        uriMatcher.addURI(AUTHORITY,TABLE_FAVORITE + "/#",FAVORITE_ID);
    }
    private FavHelper favHelper;
    @Override
    public boolean onCreate() {
        favHelper = new FavHelper(getContext());
        favHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                cursor = favHelper.query();
                break;
            case FAVORITE_ID:
                cursor = favHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);}

        return cursor;
    }

    @Nullable
    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri, ContentValues contentValues) {
        long added;

        if(uriMatcher.match(uri) == FAVORITE) {added = favHelper.insert(contentValues);}
        else {added = 0;}

        if (added > 0) {Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);}

        return Uri.parse(C_URI + "/" + added);
    }

    @Override
    public int delete( Uri uri, String s,String[] strings) {
        int deleted;
        if (uriMatcher.match(uri) == FAVORITE_ID) { deleted = favHelper.delete(uri.getLastPathSegment());}
        else {deleted = 0;}
        if (deleted > 0) {Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);}
        return deleted;
    }

    @Override
    public int update(Uri uri,ContentValues contentValues, String s, String[] strings) {
        int updated;
        if (uriMatcher.match(uri) == FAVORITE_ID) {updated = favHelper.update(uri.getLastPathSegment(),contentValues);}
        else {updated = 0;}
        if (updated > 0) {Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);}

        return updated;
    }
}
