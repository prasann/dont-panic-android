package com.thoughtworks.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.thoughtworks.R;
import com.thoughtworks.adapters.ImageAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.PlaceType;

import java.util.ArrayList;
import java.util.List;

public class HomeGridActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,getPlaceTypes()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HomeGridActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<PlaceType> getPlaceTypes() {
        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.getPlaceTypes(this);
        List<PlaceType> placeTypeList = new ArrayList<PlaceType>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            placeTypeList.add(new PlaceType(cursor));
        }
        cursor.close();
        return placeTypeList;
    }
}
