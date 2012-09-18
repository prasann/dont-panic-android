package com.thoughtworks.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.OfficeListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Office;
import com.thoughtworks.utils.Constants;
import com.thoughtworks.viewmodels.OfficeInfo;

import java.util.ArrayList;
import java.util.List;

public class ListByOfficeActivity extends ListActivity {
    private OfficeListAdapter officeListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String cityId = (String) bundle.get(Constants.CITY_ID);
        String cityName = (String) bundle.get(Constants.CITY_NAME);
        setContentView(R.layout.office_listing);
        setCountryName(cityName);
        listView(cityId);
    }

    private void setCountryName(String cityName) {
        TextView countryNameView = (TextView) findViewById(R.id.city_name);
        countryNameView.setText(cityName);
    }

    private void listView(String cityId) {
        List<OfficeInfo> cityList = getAllOffices(Integer.valueOf(cityId));
        officeListAdapter = new OfficeListAdapter(this, R.layout.row_office, cityList);
        this.setListAdapter(officeListAdapter);
    }

    private List<OfficeInfo> getAllOffices(int cityId) {
        List<OfficeInfo> officeList = new ArrayList<OfficeInfo>();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAllOffices(this, cityId);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            officeList.add(new OfficeInfo(cursor));
        }
        cursor.close();
        return officeList;
    }

}
