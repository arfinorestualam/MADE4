package com.example.apkfin4.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {


    private static String SCHEME = "content";
    public static String AUTHORITY = "com.example.apkfin4";

    public static final class Columns implements BaseColumns {

        public static String TABLE_FAVORITE = "favorite";
        public static String FAVID = "id";
        public static String BACKDROPPATH = "backdroppath";
        public static String POSTERPATH = "posterpath";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String CATEGORY = "category";

        public static Uri C_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE).build();
    }
}
