package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.OfficeListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.utils.Constants;
import com.thoughtworks.viewmodels.OfficeInfo;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.OFFICE_ID;

public class ListByOfficeActivity extends ListActivity {
    private OfficeListAdapter officeListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String cityId = (String) bundle.get(Constants.CITY_ID);
        String cityName = (String) bundle.get(Constants.CITY_NAME);
        setContentView(R.layout.office_listing);
        listView(cityId);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        OfficeInfo country = (OfficeInfo) this.getListAdapter().getItem(position);
        Intent intent = new Intent(v.getContext(), OfficeSummaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(OFFICE_ID, String.valueOf(country.getOfficeId()));
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_FIRST_USER);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private void listView(String cityId) {
        List<OfficeInfo> cityList = getAllOffices(Integer.valueOf(cityId));
//        officeListAdapter = new OfficeListAdapter(this, R.layout.row_office, cityList);
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
