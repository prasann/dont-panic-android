package com.thoughtworks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.processors.JSONParser;
import com.thoughtworks.utils.Constants;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.thoughtworks.utils.Constants.EMPTY_STRING;
import static java.net.HttpURLConnection.HTTP_OK;

public class DataSyncTask extends AsyncTask<String, String, String> {

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "0";
    private String TAG = "DataSyncTask";
    private Context context;
    private static final String WEB_URL = "http://dont-panic.herokuapp.com/data.json";


    @Override
    protected String doInBackground(String... uri) {
        int status;
        synchronized (this) {
            String json = downloadData(WEB_URL);
            if (json.equals(EMPTY_STRING)) return error();
            Map<String, Object> objectMap = parseJSON(json);
            if (objectMap == null) return error();
            status = saveData(objectMap);
            if (status == 0) return error();
            this.notify();
        }
        return SUCCESS;
    }

    private String error() {
        Log.e(TAG, "Error while syncing data");
        this.notify();
        return FAILURE;
    }

    public DataSyncTask(Context context) {
        this.context = context;
    }

    public String downloadData(String uri) {
        return getDataFromURL(uri);
    }

    public Map<String, Object> parseJSON(String json) {
        return JSONParser.parse(json);
    }

    public int saveData(Map<String, Object> objectMap) {
        int status = 0;
        try {
            DBHelper.saveObjectMap(context, objectMap);
            status = 1;
        } catch (Exception e) {
            Log.e(TAG, "Saving data failed : " + e.getMessage());
        }
        return status;
    }

    private String getDataFromURL(String uri) {
        String response = EMPTY_STRING;
        try {
            URL url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() != HTTP_OK) {
                Log.e(TAG, "Connection Establishment Failed");
                return EMPTY_STRING;
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = readStream(in);
            } catch (IOException e) {
                Log.e(TAG, "IOException while reading :" + uri);
                throw new Error(e);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e(TAG, "MalformedURLException :" + uri);
            throw new Error(e);
        }
        return response;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}