package com.thoughtworks.models;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Office {

    private static final String TABLE_NAME = "offices";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CITY_ID = "city_id";

    private int id;
    private String name;
    private String address;
    private Double longitude;
    private Double latitude;
    private String phoneNumber;
    private String email;
    private int cityId;
    private int companyId;

    public Office(int id, String name, String address, Double longitude,
                  Double latitude, String phoneNumber, String email, int cityId, int companyId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cityId = cityId;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getCityId() {
        return cityId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public static void reCreate(SQLiteDatabase db, List<Office> offices) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex(ID);
        int name_index = insertHelper.getColumnIndex(NAME);
        int address_index = insertHelper.getColumnIndex("address");
        int lat_index = insertHelper.getColumnIndex("latitude");
        int long_index = insertHelper.getColumnIndex("longitude");
        int phone_num_index = insertHelper.getColumnIndex("phone_numbers");
        int email_index = insertHelper.getColumnIndex("email");
        int cit_id_index = insertHelper.getColumnIndex(CITY_ID);
        int comp_id_index = insertHelper.getColumnIndex("company_id");
        for (Office office : offices) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, office.getId());
            insertHelper.bind(name_index, office.getName());
            insertHelper.bind(lat_index, office.getLatitude());
            insertHelper.bind(long_index, office.getLongitude());
            insertHelper.bind(address_index, office.getAddress());
            insertHelper.bind(email_index, office.getEmail());
            insertHelper.bind(phone_num_index, office.getPhoneNumber());
            insertHelper.bind(cit_id_index, office.getCityId());
            insertHelper.bind(comp_id_index, office.getCompanyId());
            insertHelper.execute();
        }
    }

    public static Cursor getAll(SQLiteDatabase mDb, int cityId) {
        String query = "select * from offices,companies where offices.company_id = companies._id and offices.city_id = ?";
        return mDb.rawQuery(query, new String[]{String.valueOf(cityId)});
    }
}