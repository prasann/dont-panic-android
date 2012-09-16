package com.thoughtworks.models;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class City {
    private static final String TABLE_NAME = "cities";

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

    public static void save(SQLiteDatabase db, List<City> cities) {
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex("_id");
        int name_index = insertHelper.getColumnIndex("name");
        int code_index = insertHelper.getColumnIndex("code");
        int country_id_index = insertHelper.getColumnIndex("country_id");
        for (City city : cities) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, city.getId());
            insertHelper.bind(name_index, city.getName());
            insertHelper.bind(code_index, city.getCode());
            insertHelper.bind(country_id_index, city.getCountryId());
            insertHelper.execute();
        }
    }

}
