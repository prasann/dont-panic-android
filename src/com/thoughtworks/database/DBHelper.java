package com.thoughtworks.database;

import android.content.Context;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Country;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.OBJ_MAP_CITIES;
import static com.thoughtworks.utils.Constants.OBJ_MAP_COUNTRIES;

public class DBHelper {

    public static void saveObjectMap(Context context, Map<String, Object> objectMap) {
        BaseDB db = new BaseDB();
        db.open(context);
        Country.save(db.mDb, (List<Country>) objectMap.get(OBJ_MAP_COUNTRIES));
        City.save(db.mDb, (List<City>) objectMap.get(OBJ_MAP_CITIES));
        db.close();
    }


}
