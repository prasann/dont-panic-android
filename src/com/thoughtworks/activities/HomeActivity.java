package com.thoughtworks.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import com.thoughtworks.R;

public class HomeActivity extends Activity {
    private Button viewAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        viewAllButton(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sync_menu, menu);
        return true;
    }

    private void viewAllButton(final Context context) {
        viewAll = (Button) findViewById(R.id.view_all);
        viewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListByCountryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
    }
}
