package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.thoughtworks.R;
import com.thoughtworks.adapters.CountryListAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.Country;
import com.thoughtworks.tasks.DataSyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListByCountryActivity extends ListActivity {
    private Button syncButton;
    private CountryListAdapter countryListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_listing);
        listView();
        syncButton(this);
    }

    private void listView() {
        List<Country> countryList = getAllCountries();
        countryListAdapter = new CountryListAdapter(this, R.layout.row_country, countryList);
        this.setListAdapter(countryListAdapter);
    }

    private void syncButton(final Context context) {
        syncButton = (Button) findViewById(R.id.synchronize);
        syncButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    new DataSyncTask(context).execute("http://dont-panic.herokuapp.com/data.json");
                } else {
                    Toast toast = Toast.makeText(context, "You need data connection to Sync content", 10);
                    toast.show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<Country>();
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getAllCountries(this);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            countryList.add(new Country(cursor));
        }
        cursor.close();
        return countryList;
    }

}
