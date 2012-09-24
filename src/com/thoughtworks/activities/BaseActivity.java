package com.thoughtworks.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.thoughtworks.R;
import com.thoughtworks.widget.ActionBar;

import static com.thoughtworks.utils.Constants.CITY_PREFS;
import static com.thoughtworks.utils.Constants.PREFS_NAME;

public class BaseActivity extends Activity {
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setActionBar() {
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(R.string.app_name);
        mActionBar.setHomeLogo(R.drawable.ic_launcher);
        mActionBar.addActionIcon(R.drawable.ic_menu_sync, new View.OnClickListener() {
            public void onClick(View view) {
                syncData();
            }
        });
    }

    public String getCity() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(CITY_PREFS, "");
    }

    private void syncData() {
        if (isNetworkAvailable()) {
            new SyncActivity(BaseActivity.this).sync();
        } else {
            Toast toast = Toast.makeText(BaseActivity.this, "You need data connection to Sync content", 15);
            toast.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
