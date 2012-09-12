package com.thoughtworks.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BaseDB {
    private final String DATABASE_NAME = "dont_panic";
    private final String DATABASE_TABLE;
    private final int DATABASE_VERSION = 1;
    private final String MIGRATION_FILE = "migration" + DATABASE_VERSION + ".sql";

    private DatabaseHelper mDbHelper;
    public SQLiteDatabase mDb;

    public BaseDB(String DATABASE_TABLE) {
        this.DATABASE_TABLE = DATABASE_TABLE;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        private final Context myContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            myContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            InputStream iStream;
            try {
                iStream = myContext.getAssets().open(MIGRATION_FILE);
                String queries = string(iStream);
                runMigrations(db, queries);
            } catch (IOException e) {
                throw new Error("Create Database Error", e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DATABASE_NAME, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        }

        private void runMigrations(SQLiteDatabase db, String myQueries) {
            String[] queries = myQueries.split(";");
            for (String query : queries) {
                db.execSQL(query);
            }
        }

        private String string(InputStream myInput) throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(myInput));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        }
    }

    public BaseDB open(Context myContext) throws SQLiteException {
        mDbHelper = new DatabaseHelper(myContext);
        try {
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDb = mDbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

}
