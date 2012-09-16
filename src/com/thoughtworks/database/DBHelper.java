package com.thoughtworks.database;

import android.content.Context;
import com.thoughtworks.models.Administrator;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Company;
import com.thoughtworks.models.Country;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.Constants.*;

public class DBHelper {

    public static void saveObjectMap(Context context, Map<String, Object> objectMap) {
        BaseDB db = new BaseDB();
        db.open(context);
        Country.save(db.mDb, (List<Country>) objectMap.get(OBJ_MAP_COUNTRIES));
        City.save(db.mDb, (List<City>) objectMap.get(OBJ_MAP_CITIES));
        Company.save(db.mDb, (List<Company>) objectMap.get(OBJ_MAP_COMPANIES));
        Administrator.save(db.mDb, (List<Administrator>) objectMap.get(OBJ_MAP_ADMINISTRATORS));
        db.close();
    }


}
