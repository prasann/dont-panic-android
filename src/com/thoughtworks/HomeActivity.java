package com.thoughtworks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
    private Button viewAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        viewAllButton(context);
    }

    private void viewAllButton(final Context context) {
        viewAll = (Button) findViewById(R.id.view_all);
        viewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListByCountryActivity.class);
                startActivity(intent);
            }
        });
    }
}
