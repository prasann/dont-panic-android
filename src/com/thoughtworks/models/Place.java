package com.thoughtworks.models;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

public class Place {

    private static final String TABLE_NAME = "places";

    private int id;
    private String name;
    private String description;
    private String address;
    private Double longitude;
    private Double latitude;
    private String phoneNumber;
    private String email;
    private int cityId;
    private int placeTypeId;

    public Place(int id, String name, String description, String address, Double longitude, Double latitude,
                 String phoneNumber, String email, int placeTypeId, int cityId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.placeTypeId = placeTypeId;
        this.cityId = cityId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public int getPlaceTypeId() {
        return placeTypeId;
    }

    public static void reCreate(SQLiteDatabase db, List<Place> places) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex("_id");
        int name_index = insertHelper.getColumnIndex("name");
        int address_index = insertHelper.getColumnIndex("address");
        int lat_index = insertHelper.getColumnIndex("latitude");
        int long_index = insertHelper.getColumnIndex("longitude");
        int phone_num_index = insertHelper.getColumnIndex("phone_numbers");
        int email_index = insertHelper.getColumnIndex("email");
        int cit_id_index = insertHelper.getColumnIndex("city_id");
        int desc_index = insertHelper.getColumnIndex("description");
        int place_type_index = insertHelper.getColumnIndex("place_type_id");
        for (Place office : places) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, office.getId());
            insertHelper.bind(name_index, office.getName());
            insertHelper.bind(desc_index, office.getDescription());
            insertHelper.bind(lat_index, office.getLatitude());
            insertHelper.bind(long_index, office.getLongitude());
            insertHelper.bind(address_index, office.getAddress());
            insertHelper.bind(email_index, office.getEmail());
            insertHelper.bind(phone_num_index, office.getPhoneNumber());
            insertHelper.bind(cit_id_index, office.getCityId());
            insertHelper.bind(place_type_index, office.getPlaceTypeId());
            insertHelper.execute();
        }
    }

}
