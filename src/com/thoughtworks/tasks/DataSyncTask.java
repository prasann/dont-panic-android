package com.thoughtworks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.processors.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class DataSyncTask extends AsyncTask<String, String, String> {

    private String TAG = "DataSyncTask";
    private String EMPTY_STRING = "";
    private Context context;

    public DataSyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... uri) {
        String json = getDataFromURL(uri[0]);
        Map<String, Object> objectMap = JSONParser.parse(json);
        DBHelper.saveObjectMap(context, objectMap);
        return json;
    }

    private String getDataFromURL(String spec) {
        String response = EMPTY_STRING;
        try {
            URL url = new URL(spec);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() != HTTP_OK) {
                Log.e(TAG, "Connection Establishment Failed");
                return EMPTY_STRING;
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = readStream(in);
            } catch (IOException e) {
                Log.e(TAG, "IOException while reading :" + spec);
                throw new Error(e);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e(TAG, "MalformedURLException :" + spec);
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}