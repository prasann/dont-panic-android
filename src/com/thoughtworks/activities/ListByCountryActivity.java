package com.thoughtworks.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.thoughtworks.R;
import com.thoughtworks.tasks.DataSyncTask;

public class ListByCountryActivity extends Activity {
    private ListView listView;
    private Button syncButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_listing);
        listView();
        syncButton(this);
    }

    private void listView() {
        String[] values = new String[]{"India", "America", "Australia"};
        listView = (ListView) findViewById(R.id.country_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    private void syncButton(final Context context) {
        syncButton = (Button) findViewById(R.id.synchronize);
        syncButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    new DataSyncTask().execute("http://dont-panic.herokuapp.com/data.json");
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

}
