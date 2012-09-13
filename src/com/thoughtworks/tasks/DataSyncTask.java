package com.thoughtworks.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataSyncTask extends AsyncTask<String, String, String> {

    private String TAG = "DataSyncTask";

    @Override
    protected String doInBackground(String... uri) {
        String json = getDataFromURL(uri[0]);

        return json;
    }

    private String getDataFromURL(String spec) {
        String response = "";
        try {
            URL url = new URL(spec);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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