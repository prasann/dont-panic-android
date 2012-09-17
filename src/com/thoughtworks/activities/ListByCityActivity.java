package com.thoughtworks.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.CityListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListByCityActivity extends ListActivity {
    private CityListAdapter cityListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String countryId = (String) bundle.get(Constants.COUNTRY_ID);
        String countryName = (String) bundle.get(Constants.COUNTRY_NAME);
        setContentView(R.layout.city_listing);
        setCountryName(countryName);
        listView(countryId);
    }

    private void setCountryName(String countryName) {
        TextView countryNameView = (TextView) findViewById(R.id.country_name);
        countryNameView.setText(countryName);
    }

    private void listView(String countryId) {
        List<City> cityList = getAllCities(Integer.valueOf(countryId));
        cityListAdapter = new CityListAdapter(this, R.layout.row_city, cityList);
        this.setListAdapter(cityListAdapter);
    }

    private List<City> getAllCities(int countryId) {
        List<City> cityList = new ArrayList<City>();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAllCities(this, countryId);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            cityList.add(new City(cursor));
        }
        cursor.close();
        return cityList;
    }

}
