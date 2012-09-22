package com.thoughtworks.models;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class City {
    private static final String TABLE_NAME = "cities";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String COUNTRY_ID = "country_id";

    private int id;
    private String name;
    private String code;
    private int countryId;

    public City(int id, String name, String code, int countryId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.countryId = countryId;
    }

    public City(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(City.ID));
        this.name = cursor.getString(cursor.getColumnIndex(City.NAME));
        this.code = cursor.getString(cursor.getColumnIndex(City.CODE));
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

    public int getCountryId() {
        return countryId;
    }

    public static void reCreate(SQLiteDatabase db, List<City> cities) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex(ID);
        int name_index = insertHelper.getColumnIndex(NAME);
        int code_index = insertHelper.getColumnIndex(CODE);
        int country_id_index = insertHelper.getColumnIndex(COUNTRY_ID);
        for (City city : cities) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, city.getId());
            insertHelper.bind(name_index, city.getName());
            insertHelper.bind(code_index, city.getCode());
            insertHelper.bind(country_id_index, city.getCountryId());
            insertHelper.execute();
        }
    }

    public static Cursor getAll(SQLiteDatabase mDb) {
        return mDb.query(TABLE_NAME, new String[]{ID, NAME, CODE}, null, null, null, null, NAME);
    }
}
