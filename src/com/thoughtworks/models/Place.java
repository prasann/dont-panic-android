package com.thoughtworks.models;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Place {

    private static final String TABLE_NAME = "places";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String PHONE_NUMBERS = "phone_numbers";
    public static final String EMAIL = "email";
    public static final String CITY_ID = "city_id";
    public static final String MAP_LINK = "map";
    public static final String DESCRIPTION = "description";
    public static final String PLACE_TYPE_ID = "place_type_id";

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
    private String mapLink;

    public Place(int id, String name, String description, String address, Double longitude, Double latitude,
                 String phoneNumber, String email, String mapLink, int placeTypeId, int cityId) {
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
        this.mapLink = mapLink;
    }

    public Place(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(ID));
        this.name = cursor.getString(cursor.getColumnIndex(NAME));
        this.address = cursor.getString(cursor.getColumnIndex(ADDRESS));
        this.latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
        this.longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
        this.phoneNumber = cursor.getString(cursor.getColumnIndex(PHONE_NUMBERS));
        this.email = cursor.getString(cursor.getColumnIndex(EMAIL));
        this.mapLink = cursor.getString(cursor.getColumnIndex(MAP_LINK));
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

    public String getMapLink() {
        return mapLink;
    }

    public static void reCreate(SQLiteDatabase db, List<Place> places) {
        db.delete(TABLE_NAME, null, null);
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(db, TABLE_NAME);
        int id_index = insertHelper.getColumnIndex(ID);
        int name_index = insertHelper.getColumnIndex(NAME);
        int address_index = insertHelper.getColumnIndex(ADDRESS);
        int lat_index = insertHelper.getColumnIndex(LATITUDE);
        int long_index = insertHelper.getColumnIndex(LONGITUDE);
        int phone_num_index = insertHelper.getColumnIndex(PHONE_NUMBERS);
        int email_index = insertHelper.getColumnIndex(EMAIL);
        int cit_id_index = insertHelper.getColumnIndex(CITY_ID);
        int desc_index = insertHelper.getColumnIndex(DESCRIPTION);
        int place_type_index = insertHelper.getColumnIndex(PLACE_TYPE_ID);
        int map_index = insertHelper.getColumnIndex(MAP_LINK);
        for (Place place : places) {
            insertHelper.prepareForInsert();
            insertHelper.bind(id_index, place.getId());
            insertHelper.bind(name_index, place.getName());
            insertHelper.bind(desc_index, place.getDescription());
            insertHelper.bind(lat_index, place.getLatitude());
            insertHelper.bind(long_index, place.getLongitude());
            insertHelper.bind(address_index, place.getAddress());
            insertHelper.bind(email_index, place.getEmail());
            insertHelper.bind(phone_num_index, place.getPhoneNumber());
            insertHelper.bind(cit_id_index, place.getCityId());
            insertHelper.bind(place_type_index, place.getPlaceTypeId());
            insertHelper.bind(map_index, place.getMapLink());
            insertHelper.execute();
        }
    }

    public static Cursor getPlace(SQLiteDatabase mDb, int cityId, String placeType) {
        String query = "select places.* from places,place_types where places.city_id = ? " +
                "and places.place_type_id = place_types._id and place_types.name = ?";
        return mDb.rawQuery(query, new String[]{String.valueOf(cityId), placeType});
    }

}
