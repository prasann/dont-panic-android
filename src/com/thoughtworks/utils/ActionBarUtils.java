package com.thoughtworks.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;

import static com.thoughtworks.utils.Constants.CITY_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class ActionBarUtils {

    private Activity activity;

    public ActionBarUtils(Activity activity) {
        this.activity = activity;
    }

    public String getCity() {
        String city = retrieveFromSharedPreference();
        if (city.equals("")) {
            updateSharedPreference(getCityFromDB());
        }
        return retrieveFromSharedPreference();
    }

    private void updateSharedPreference(City city) {
        if (city == null)
            return;
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(Constants.CITY_PREFS, city.getName());
        edit.putInt(Constants.CITY_ID_PREFS, city.getId());
        edit.commit();
    }

    private String retrieveFromSharedPreference() {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(CITY_PREFS, "");
    }

    private City getCityFromDB() {
        Cursor cursor = new DBHelper().getACity(activity);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        City city = new City(cursor);
        cursor.close();
        return city;
    }
}
