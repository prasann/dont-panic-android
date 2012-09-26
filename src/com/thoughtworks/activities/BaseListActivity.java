package com.thoughtworks.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.thoughtworks.R;
import com.thoughtworks.utils.ActionBarUtils;
import com.thoughtworks.widget.ActionBar;

public class BaseListActivity extends ListActivity {
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setActionBar(String actionTitle) {
        ActionBarUtils actionBarUtils = new ActionBarUtils(this);
        mActionBar = (ActionBar) findViewById(R.id.actionBar);
        mActionBar.setTitle(actionTitle);
        mActionBar.setHomeLogo(R.drawable.home, homeLogoListener());
        mActionBar.addActionButton(actionBarUtils.getCity(), new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ListCitiesActivity.class);
                startActivityForResult(intent, RESULT_FIRST_USER);
            }
        });
    }

    private View.OnClickListener homeLogoListener() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, RESULT_FIRST_USER);
                finish();
            }
        };
    }


}
