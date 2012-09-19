package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.utils.Constants.COUNTRY_ID;
import static com.thoughtworks.utils.Constants.COUNTRY_NAME;

public class ListByCountryActivity extends ListActivity {
    private Button syncButton;
    private CountryListAdapter customListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_listing);
        listView();
        syncButton(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Country country = (Country) this.getListAdapter().getItem(position);
        Intent intent = new Intent(v.getContext(), ListByCityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_ID, String.valueOf(country.getId()));
        bundle.putString(COUNTRY_NAME, String.valueOf(country.getName()));
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_FIRST_USER);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private void listView() {
        List<Country> countryList = getAllCountries();
        customListAdapter = new CountryListAdapter(this, R.layout.row_country, countryList);
        this.setListAdapter(customListAdapter);
    }

    private void syncButton(final Context context) {
        syncButton = (Button) findViewById(R.id.synchronize);
        syncButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    new SyncActivity(context).sync();
                } else {
                    Toast toast = Toast.makeText(context, "You need data connection to Sync content", 15);
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
