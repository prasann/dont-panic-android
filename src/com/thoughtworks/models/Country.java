package com.thoughtworks.models;

import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Country {

    private static final String TABLE_NAME = "countries";

    private int id;
    private String name;
    private String code;

    public Country(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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
        int id_index = insertHelper.getColumnIndex("_id");
        int name_index = insertHelper.getColumnIndex("name");
        int code_index = insertHelper.getColumnIndex("code");
        for (Country country : countries) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, country.getId());
            insertHelper.bind(name_index, country.getName());
            insertHelper.bind(code_index, country.getCode());
            insertHelper.execute();
        }
    }
}
