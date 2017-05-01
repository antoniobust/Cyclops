package com.mdmobile.pocketconsole.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class McProvider extends ContentProvider {

    //Matcher
    private static McUriMatcher matcher;

    //DB helper
    private McHelper mcHelper;


    @Override
    public boolean onCreate() {
        mcHelper = new McHelper(getContext());
        matcher = new McUriMatcher();
        return true;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return matcher.matchUri(uri).contentType;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
