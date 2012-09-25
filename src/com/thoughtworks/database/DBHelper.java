package com.thoughtworks.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.thoughtworks.models.*;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.*;

public class DBHelper {

    private SQLiteDatabase mDb;

    public static void saveObjectMap(Context context, Map<String, Object> objectMap) {
        BaseDB db = new BaseDB();
        db.open(context);
        Country.reCreate(db.mDb, (List<Country>) objectMap.get(OBJ_MAP_COUNTRIES));
        City.reCreate(db.mDb, (List<City>) objectMap.get(OBJ_MAP_CITIES));
        Company.reCreate(db.mDb, (List<Company>) objectMap.get(OBJ_MAP_COMPANIES));
        Administrator.reCreate(db.mDb, (List<Administrator>) objectMap.get(OBJ_MAP_ADMINISTRATORS));
        Office.reCreate(db.mDb, (List<Office>) objectMap.get(OBJ_MAP_OFFICES));
        Place.reCreate(db.mDb, (List<Place>) objectMap.get(OBJ_MAP_PLACES));
        PlaceType.reCreate(db.mDb, (List<PlaceType>) objectMap.get(OBJ_MAP_PLACE_TYPES));
        db.close();
    }

    public Cursor getACity(Context context) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return City.getACity(db.mDb);
    }

    public Cursor getAllCities(Context context) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return City.getAll(db.mDb);
    }

    public Cursor getAllOffices(Context context, int cityId) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return Office.getAll(db.mDb, cityId);
    }

    public Cursor getPlaceTypes(Context context) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return PlaceType.getAll(db.mDb);
    }

    public Cursor getAdministratorsBy(int cityId, Context context) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return Administrator.getAll(db.mDb, cityId);
    }

    public Cursor getPlace(Context context, int cityId, String placeType) {
        BaseDB db = new BaseDB();
        db.open(context);
        this.mDb = db.mDb;
        return Place.getPlace(db.mDb, cityId, placeType);
    }

    public void close() {
        this.mDb.close();
    }


}
