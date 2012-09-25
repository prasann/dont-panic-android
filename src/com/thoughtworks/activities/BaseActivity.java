package com.thoughtworks.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.thoughtworks.R;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;
import com.thoughtworks.utils.Constants;
import com.thoughtworks.widget.ActionBar;

import static com.thoughtworks.utils.Constants.CITY_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class BaseActivity extends Activity {
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setActionBar() {
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(R.string.app_name);
        mActionBar.setHomeLogo(R.drawable.panic);
        mActionBar.addActionIcon(R.drawable.ic_menu_sync, new View.OnClickListener() {
            public void onClick(View view) {
                startSplashScreen();
            }
        });
    }

    public String getCity() {
        String city = retrieveFromSharedPreference();
        if (city.equals("")) {
            updateSharedPreference(getCityFromDB());
        }
        return retrieveFromSharedPreference();
    }

    public boolean haveData() {
        boolean status;
        DBHelper dbHelper = new DBHelper();
        Cursor allCities = dbHelper.getAllCities(this);
        status = allCities.getCount() > 0;
        allCities.close();
        dbHelper.close();
        return status;
    }


    private void updateSharedPreference(City city) {
        if (city == null)
            return;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(Constants.CITY_PREFS, city.getName());
        edit.putInt(Constants.CITY_ID_PREFS, city.getId());
        edit.commit();
    }

    private String retrieveFromSharedPreference() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(CITY_PREFS, "");
    }

    private City getCityFromDB() {
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getACity(this);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        City city = new City(cursor);
        cursor.close();
        dbHelper.close();
        return city;
    }

    private void startSplashScreen() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}
