package com.thoughtworks.models;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Company {
    private static final String TABLE_NAME = "companies";
    public static final String NAME = "name";

    private int id;
    private String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void reCreate(SQLiteDatabase db, List<Company> companies) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex("_id");
        int name_index = insertHelper.getColumnIndex(NAME);
        for (Company company : companies) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, company.getId());
            insertHelper.bind(name_index, company.getName());
            insertHelper.execute();
        }
    }

}
