package com.thoughtworks.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
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

public class OfficePlaceSummaryActivity extends BaseListActivity {
    private OfficeListAdapter officeListAdapter;
    private PlaceListAdapter placeListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.summary_listing);
        if (bundle == null) {
            officeView();
        } else {
            String placeType = (String) bundle.get(Constants.PLACE_TYPE);
            placeTypeView(placeType);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private void placeTypeView(String placeType) {
        setActionBar(placeType + " Information");
        setEmptyListText("No " + placeType + " found.");
        List<Place> cityList = getPlaces(placeType);
        placeListAdapter = new PlaceListAdapter(this, R.layout.row_officeplace, cityList);
        this.setListAdapter(placeListAdapter);
    }

    private List<Place> getPlaces(String placeType) {
        int cityId = getCityPreference();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getPlace(this, cityId, placeType);
        List<Place> placeList = new ArrayList<Place>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            placeList.add(new Place(cursor));
        }
        cursor.close();
        dbHelper.close();
        return placeList;
    }

    private int getCityPreference() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(CITY_ID_PREFS, 1);
    }

    private void officeView() {
        setActionBar("Office Information");
        setEmptyListText("No Offices found.");
        List<Office> cityList = getOffices();
        officeListAdapter = new OfficeListAdapter(this, R.layout.row_officeplace, cityList);
        this.setListAdapter(officeListAdapter);
    }

    private void setEmptyListText(String text) {
        TextView emptyText = (TextView) findViewById(R.id.empty_text);
        emptyText.setText(text);
    }


    private List<Office> getOffices() {
        int cityId = getCityPreference();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAllOffices(this, cityId);
        List<Office> officeList = new ArrayList<Office>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            officeList.add(new Office(cursor));
        }
        cursor.close();
        dbHelper.close();
        return officeList;
    }


}
