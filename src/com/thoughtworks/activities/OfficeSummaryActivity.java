package com.thoughtworks.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.database.DBHelper;
import com.thoughtworks.models.Office;
import com.thoughtworks.utils.Constants;

public class OfficeSummaryActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String officeId = (String) bundle.get(Constants.OFFICE_ID);
        setContentView(R.layout.office_summary);
        populateViewLayout(getOffice(officeId));
    }

    private void populateViewLayout(Office office) {
        ((TextView) findViewById(R.id.sum_off_name)).setText(office.getName());
        ((TextView) findViewById(R.id.sum_off_address)).setText(office.getAddress());
        ((TextView) findViewById(R.id.sum_off_latitude)).setText(office.getLatitude().toString());
        ((TextView) findViewById(R.id.sum_off_longitude)).setText(office.getLongitude().toString());
        ((TextView) findViewById(R.id.sum_off_phone)).setText(office.getPhoneNumber());
        ((TextView) findViewById(R.id.sum_off_email)).setText(office.getEmail());
    }

    private Office getOffice(String officeId) {
        Cursor cursor = new DBHelper().getOffice(this, Integer.valueOf(officeId));
        cursor.moveToFirst();
        Office office = new Office(cursor);
        cursor.close();
        return office;
    }


}
