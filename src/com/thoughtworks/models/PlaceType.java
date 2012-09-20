package com.thoughtworks.models;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class PlaceType {
    private static final String TABLE_NAME = "place_types";

    public static final String ID = "_id";
    public static final String NAME = "name";


    private int id;
    private String name;

    public PlaceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlaceType(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(Country.ID));
        this.name = cursor.getString(cursor.getColumnIndex(Country.NAME));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void reCreate(SQLiteDatabase db, List<PlaceType> placeTypes) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex(ID);
        int name_index = insertHelper.getColumnIndex(NAME);
        for (PlaceType placeType : placeTypes) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, placeType.getId());
            insertHelper.bind(name_index, placeType.getName());
            insertHelper.execute();
        }
    }

    public static Cursor getAll(SQLiteDatabase mDb) {
        return mDb.query(TABLE_NAME, new String[]{ID, NAME}, null, null, null, null, NAME);
    }
}
