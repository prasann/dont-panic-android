package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import com.thoughtworks.R;
import com.thoughtworks.adapters.OfficeListAdapter;
import com.thoughtworks.adapters.PlaceListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.Office;
import com.thoughtworks.models.Place;
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.CITY_ID_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class OfficeSummaryActivity extends ListActivity {
    private OfficeListAdapter officeListAdapter;
    private PlaceListAdapter placeListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.office_listing);
        if (bundle == null) {
            officeView();
        } else {
            String placeType = (String) bundle.get(Constants.PLACE_TYPE);
            placeTypeView(placeType);
        }
    }

    private void placeTypeView(String placeType) {
        List<Place> cityList = getPlaces(placeType);
        placeListAdapter = new PlaceListAdapter(this, R.layout.row_office, cityList);
        this.setListAdapter(placeListAdapter);
    }

    private List<Place> getPlaces(String placeType) {
        int cityId = getCityPrefs();
        Cursor cursor = new DBHelper().getPlace(this, cityId, placeType);
        List<Place> placeList = new ArrayList<Place>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            placeList.add(new Place(cursor));
        }
        cursor.close();
        return placeList;
    }

    private int getCityPrefs() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(CITY_ID_PREFS, 1);
    }

    private void officeView() {
        List<Office> cityList = getOffices();
        officeListAdapter = new OfficeListAdapter(this, R.layout.row_office, cityList);
        this.setListAdapter(officeListAdapter);
    }


    private List<Office> getOffices() {
        int cityId = getCityPrefs();
        Cursor cursor = new DBHelper().getAllOffices(this, cityId);
        List<Office> officeList = new ArrayList<Office>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            officeList.add(new Office(cursor));
        }
        cursor.close();
        return officeList;
    }


}
