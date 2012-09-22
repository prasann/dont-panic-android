package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import com.thoughtworks.R;
import com.thoughtworks.adapters.OfficeListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.Office;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.CITY_ID_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class OfficeSummaryActivity extends ListActivity {
    private OfficeListAdapter officeListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.office_listing);
        listView();
    }

    private int getCityPrefs() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(CITY_ID_PREFS, 1);
    }

    private void listView() {
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
