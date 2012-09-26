package com.thoughtworks.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.adapters.AdminListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.Administrator;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.CITY_ID_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class AdminSummaryActivity extends BaseListActivity {
    private static final String ACTION_TITLE = "Administrators";
    private static final String NO_ADMINISTRATORS_FOUND = "No Administrators found.";

    private AdminListAdapter adminListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_listing);
        setActionBar(ACTION_TITLE);
        TextView emptyText = (TextView) findViewById(R.id.empty_text);
        emptyText.setText(NO_ADMINISTRATORS_FOUND);
        listView();
    }

    private int getCityPreference() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(CITY_ID_PREFS, 1);
    }

    private void listView() {
        List<Administrator> cityList = getAdministrators();
        adminListAdapter = new AdminListAdapter(this, R.layout.row_admin, cityList);
        this.setListAdapter(adminListAdapter);
    }


    private List<Administrator> getAdministrators() {
        int cityId = getCityPreference();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAdministratorsBy(cityId, this);
        List<Administrator> adminList = new ArrayList<Administrator>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            adminList.add(new Administrator(cursor));
        }
        cursor.close();
        dbHelper.close();
        return adminList;
    }
}
