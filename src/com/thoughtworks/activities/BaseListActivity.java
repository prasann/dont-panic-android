package com.thoughtworks.activities;

import android.app.ListActivity;
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

public class BaseListActivity extends ListActivity {
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setActionBar(String actionTitle) {
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(actionTitle);
        mActionBar.setHomeLogo(R.drawable.home, homeLogoListener());
        mActionBar.addActionButton(getCity(), null);
    }

    private View.OnClickListener homeLogoListener() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, RESULT_FIRST_USER);
                finish();
            }
        };
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
        Cursor cursor = new DBHelper().getACity(this);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        City city = new City(cursor);
        cursor.close();
        return city;
    }
}
