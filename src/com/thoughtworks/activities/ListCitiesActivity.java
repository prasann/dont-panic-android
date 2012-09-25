package com.thoughtworks.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.CityListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class ListCitiesActivity extends BaseListActivity {
    private CityListAdapter cityListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_listing);
        setActionBar("Choose a City");
        listView();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        City city = (City) this.getListAdapter().getItem(position);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(Constants.CITY_PREFS, city.getName());
        edit.putInt(Constants.CITY_ID_PREFS, city.getId());
        edit.commit();
        Intent intent = new Intent(v.getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, RESULT_FIRST_USER);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private void listView() {
        List<City> cityList = getAllCities();
        cityListAdapter = new CityListAdapter(this, R.layout.row_city, cityList);
        this.setListAdapter(cityListAdapter);
    }

    private List<City> getAllCities() {
        List<City> cityList = new ArrayList<City>();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAllCities(this);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            cityList.add(new City(cursor));
        }
        cursor.close();
        dbHelper.close();
        return cityList;
    }

}
