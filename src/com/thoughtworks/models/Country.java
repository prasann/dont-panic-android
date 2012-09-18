package com.thoughtworks.models;

import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Country {

    private static final String TABLE_NAME = "countries";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CODE = "code";

    private int id;
    private String name;
    private String code;

    public Country(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Country(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(Country.ID));
        this.name = cursor.getString(cursor.getColumnIndex(Country.NAME));
        this.code = cursor.getString(cursor.getColumnIndex(Country.CODE));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static void reCreate(SQLiteDatabase db, List<Country> countries) {
        db.delete(TABLE_NAME, null, null);
        InsertHelper insertHelper = new InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex(ID);
        int name_index = insertHelper.getColumnIndex(NAME);
        int code_index = insertHelper.getColumnIndex(CODE);
        for (Country country : countries) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, country.getId());
            insertHelper.bind(name_index, country.getName());
            insertHelper.bind(code_index, country.getCode());
            insertHelper.execute();
        }
    }

    public static Cursor getAll(SQLiteDatabase mDb) {
        return mDb.query(TABLE_NAME, new String[]{ID, NAME, CODE}, null, null, null, null, NAME);
    }
}
