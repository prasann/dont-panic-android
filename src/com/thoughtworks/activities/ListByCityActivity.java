package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.CityListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.CITY_ID;
import static com.thoughtworks.utils.Constants.CITY_NAME;

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        City city = (City) this.getListAdapter().getItem(position);
        Intent intent = new Intent(v.getContext(), ListByOfficeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CITY_ID, String.valueOf(city.getId()));
        bundle.putString(CITY_NAME, String.valueOf(city.getName()));
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_FIRST_USER);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
