package com.thoughtworks.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.thoughtworks.tasks.DataSyncTask;

import java.util.Map;

public class SyncActivity{

    private static final String WEB_URL = "http://dont-panic.herokuapp.com/data.json";
    private ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private Context context;

    public SyncActivity(Context context) {
        this.context = context;
    }

    public void sync() {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setTitle("Sync data...");
        progressBar.show();
        fetchDataThread().start();
    }

    private Thread fetchDataThread() {
        return new Thread(new Runnable() {
            public void run() {
                DataSyncTask dataSyncTask = new DataSyncTask(context);
                String json = downloadData(dataSyncTask);
                if (json == null) return;
                progressAndMessage(33, "Parsing information...");
                Map<String, Object> stringObjectMap = parseData(dataSyncTask, json);
                if (stringObjectMap == null) return;
                progressAndMessage(66, "Saving information...");
                if (!saveData(dataSyncTask, stringObjectMap)) return;
                progressBar.setProgress(100);
                progressBar.dismiss();
            }
        });
    }

    private void progressAndMessage(final int progress, final String message) {
        progressBarHandler.post(new Runnable() {
            public void run() {
                progressBar.setProgress(progress);
                progressBar.setTitle(message);
            }
        });
    }

    private String downloadData(DataSyncTask dataSyncTask) {
        String json = dataSyncTask.downloadData(WEB_URL);
        if (json.equals("")) {
            error("Some issue in downloading data. Please try after sometime.");
            return null;
        }
        return json;
    }

    private Map<String, Object> parseData(DataSyncTask dataSyncTask, String json) {
        Map<String, Object> stringObjectMap = dataSyncTask.parseJSON(json);
        if (stringObjectMap == null) {
            error("Parsing the data failed.");
            return null;
        }
        return stringObjectMap;
    }

    private boolean saveData(DataSyncTask dataSyncTask, Map<String, Object> stringObjectMap) {
        int status = dataSyncTask.saveData(stringObjectMap);
        if (status == 0) {
            error("Saving the fetched data failed.");
            return false;
        }
        return true;
    }

    public void error(String text) {
        progressBar.dismiss();
        Toast toast = Toast.makeText(context, text, 15);
        toast.show();
    }


}