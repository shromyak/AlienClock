package com.svyat.sample.alienclock.data;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract;

import static com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract.AUTHORITY;
import static com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract.MIME_TYPE_DIR;
import static com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract.MIME_TYPE_ITEM;
import static com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract.TABLE_BLOOMCHAN;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Main persistence of this application
 * At the moment contains only data for Bloomberg channel information
 */
public class AlienContentProvider extends ContentProvider {

    private static final String LOG_TAG = AlienContentProvider.class.getSimpleName();

    private static UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_BLOOMCHAN, 1);
        uriMatcher.addURI(AUTHORITY, TABLE_BLOOMCHAN + "/#", 2);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int res = uriMatcher.match(uri);

        if (res < 1) {

            return null;
        }

        try {

            if (TextUtils.isEmpty(sortOrder)) {

                sortOrder = BloomchanDataContract.Bloomchan.CREATED;
            }

            return getDatabase().query(true, TABLE_BLOOMCHAN, projection
                    , selection, selectionArgs, null, null, sortOrder, null);

        } catch (Exception ex) {

            Log.e(LOG_TAG, "Something went wrong querying messagedata", ex);

            return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int res = uriMatcher.match(uri);

        switch (res) {
            case 1: return MIME_TYPE_DIR;
            case 2: return MIME_TYPE_ITEM;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        int res = uriMatcher.match(uri);

        if (res < 1) {

            return null;
        }

        try {

            SQLiteDatabase db = getDatabase();
            db.beginTransaction();
            long recno = getDatabase().insertWithOnConflict(BloomchanDataContract.TABLE_BLOOMCHAN, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
            db.endTransaction();

            Uri record = null;
            if (recno >= 0) {
                record = Uri.parse("content://"+AUTHORITY+"/"+ BloomchanDataContract.TABLE_BLOOMCHAN +"/"+recno);
            }

            return record;

        } catch (Exception ex) {

            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int res = uriMatcher.match(uri);

        if (res < 1) {

            return 0;
        }

        try {

            SQLiteDatabase db = getDatabase();
            db.beginTransaction();
            int cnt = getDatabase().delete(TABLE_BLOOMCHAN, selection, selectionArgs);
            db.setTransactionSuccessful();
            db.endTransaction();
            return cnt;

        } catch (Exception ex) {

            return 0;
        }

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int res = uriMatcher.match(uri);

        if (res < 1) {

            return 0;
        }

        try {

            SQLiteDatabase db = getDatabase();
            db.beginTransaction();
            int cnt = getDatabase().updateWithOnConflict(TABLE_BLOOMCHAN, values, selection, selectionArgs, SQLiteDatabase.CONFLICT_NONE);
            db.setTransactionSuccessful();
            db.endTransaction();
            return cnt;

        } catch (Exception ex) {

            return 0;
        }
    }

    private static final String CREATE_TABLE_MESSAGEDATA = "CREATE TABLE bloomchan (_id INTEGER PRIMARY KEY, created INTEGER, title TEXT, pubDate TEXT, link TEXT, enclosure TEXT, guid TEXT UNIQUE ON CONFLICT IGNORE)";

    private static final String CREATE_INDEX_MESSAGEDATA = "CREATE INDEX bloomchan_index ON bloomchan(created, link)";

    private static final String DB_FILENAME = "alien_data.db";

    private MessageDataDbHelper messageDataDbHelper;

    private SQLiteDatabase database;

    private MessageDataDbHelper getMessageDataDbHelper() {

        if (messageDataDbHelper == null) {

            messageDataDbHelper = new MessageDataDbHelper(getContext());
        }

        return messageDataDbHelper;
    }

    private SQLiteDatabase getDatabase() {

        if (database == null) {

            database = getMessageDataDbHelper().getWritableDatabase();
        }

        return database;
    }

    private final class MessageDataDbHelper extends SQLiteOpenHelper {


        MessageDataDbHelper(Context paramContext) {

            super(paramContext, DB_FILENAME, null, 1);
        }

        @SuppressLint("SQLiteString")
        public final void onCreate(SQLiteDatabase database) {

            database.execSQL(CREATE_TABLE_MESSAGEDATA);
            database.execSQL(CREATE_INDEX_MESSAGEDATA);
        }

        public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int oldVersion, int newVersion) {

        }
    }
}
