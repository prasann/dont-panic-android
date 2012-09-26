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
import com.thoughtworks.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSyncCheck();
        if (hasData()) {
            renderHomePage();
        } else {
            renderNoDataPage();
        }
    }

    private void renderNoDataPage() {
        setContentView(R.layout.nodata);
        setActionBar();
    }

    private void renderHomePage() {
        setContentView(R.layout.home);
        setActionBar();
        cityButton();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, getPlaceTypes()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0:
                        intent = new Intent(v.getContext(), OfficePlaceSummaryActivity.class);
                        startActivityForResult(intent, RESULT_FIRST_USER);
                        break;
                    case 1:
                        intent = new Intent(v.getContext(), AdminSummaryActivity.class);
                        startActivityForResult(intent, RESULT_FIRST_USER);
                        break;
                    default:
                        intent = new Intent(v.getContext(), OfficePlaceSummaryActivity.class);
                        List<PlaceType> placeTypes = getPlaceTypes();
                        PlaceType placeType = placeTypes.get(position - 2);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.PLACE_TYPE, placeType.getName());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, RESULT_FIRST_USER);
                }
            }
        });
    }

    private void cityButton() {
        Button cityBtn = (Button) findViewById(R.id.city_btn);
        cityBtn.setText(getCity());
        cityBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ListCitiesActivity.class);
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
        dbHelper.close();
        return placeTypeList;
    }

    private void dataSyncCheck() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String dataSyncFailed = (String) extras.get(Constants.FAILED);
            if (dataSyncFailed != null && dataSyncFailed.equals(Constants.FAILED)) {
                Toast toast = Toast.makeText(this, "You need data connection to Sync content", 15);
                toast.show();
            }
        }
    }
}
