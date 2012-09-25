package com.thoughtworks.activities;

import android.app.Activity;
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

public class SplashScreenActivity extends Activity {

    private static final String WEB_URL = "http://dont-panic.herokuapp.com/data.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        if (isNetworkAvailable()) {
            dataSyncThread().start();
        } else {
            startHomeActivity(true);
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
                            task = new DataSyncTask(SplashScreenActivity.this).execute(WEB_URL);
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