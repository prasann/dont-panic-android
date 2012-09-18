package com.thoughtworks.database;

import android.content.Context;
import android.database.Cursor;
import com.thoughtworks.models.*;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.*;

public class DBHelper {

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

    public Cursor getAllCountries(Context context) {
        BaseDB db = new BaseDB();
        db.open(context);
        return Country.getAllCountries(db.mDb);
    }

    public Cursor getAllCities(Context context, int countryId) {
        BaseDB db = new BaseDB();
        db.open(context);
        return City.getAllCities(db.mDb, countryId);
    }

}
