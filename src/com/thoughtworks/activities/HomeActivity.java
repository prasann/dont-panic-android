package com.thoughtworks.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import com.thoughtworks.R;
import com.thoughtworks.adapters.ImageAdapter;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.PlaceType;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setActionBar();
        cityButton();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, getPlaceTypes()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(HomeActivity.this, "Favourites", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent = new Intent(v.getContext(), OfficeSummaryActivity.class);
                        startActivityForResult(intent, RESULT_FIRST_USER);
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cityButton() {
        Button cityBtn = (Button) findViewById(R.id.city_btn);
        cityBtn.setText(getCity());
        cityBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AllCitiesActivity.class);
                startActivityForResult(intent, RESULT_FIRST_USER);
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
