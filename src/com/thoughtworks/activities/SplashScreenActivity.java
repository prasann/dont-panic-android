package com.thoughtworks.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import com.thoughtworks.R;
import com.thoughtworks.tasks.DataSyncTask;
import com.thoughtworks.utils.Constants;

public class SplashScreenActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        boolean forceTrigger = forceTrigger();
        boolean dataNotPresent = !hasData();
        boolean networkAvailable = isNetworkAvailable();
        if (networkAvailable && (forceTrigger || dataNotPresent)) {
            dataSyncThread().start();
        } else {
            startHomeActivity(dataNotPresent);
        }
    }

    private Thread dataSyncThread() {
        return new Thread() {
            private Handler mHandler = new Handler(Looper.getMainLooper());
            AsyncTask<String, String, String> task;
            boolean status = true;

            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        try {
                            task = new DataSyncTask(SplashScreenActivity.this).execute();
                            synchronized (task) {
                                task.wait();
                            }
                        } catch (InterruptedException e) {
                            status = false;
                            throw new Error(e);
                        } finally {
                            startHomeActivity(!status);
                        }
                    }
                });
            }
        };
    }

    private boolean forceTrigger() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String forceTrigger = (String) extras.get(Constants.FORCE_TRIGGER);
            return (forceTrigger != null && forceTrigger.equals(Constants.FORCE_TRIGGER));
        }
        return false;
    }

    private void startHomeActivity(boolean failed) {
        finish();
        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (failed) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.FAILED, Constants.FAILED);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, RESULT_FIRST_USER);
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}