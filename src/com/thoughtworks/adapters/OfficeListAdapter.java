package com.thoughtworks.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.Office;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OfficeListAdapter extends ArrayAdapter<Office> {
    private List<Office> items;
    private Activity activity;
    private int layoutView;

    public OfficeListAdapter(Activity activity, int textViewResourceId, List<Office> items) {
        super(activity, textViewResourceId, items);
        this.items = items;
        this.activity = activity;
        this.layoutView = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(layoutView, null);
        }
        Office office = items.get(position);
        if (office != null) {
            TextView name_view = (TextView) view.findViewById(R.id.row_office_name);
            TextView address_view = (TextView) view.findViewById(R.id.row_office_address);
            TextView phone_view = (TextView) view.findViewById(R.id.row_office_phone);
            TextView email_view = (TextView) view.findViewById(R.id.row_office_email);
            TextView map_view = (TextView) view.findViewById(R.id.row_office_map);
            setButtonClick(map_view, office.getMapLink());
            setFields(String.valueOf(office.getName()), name_view);
            setFields(String.valueOf(office.getAddress()), address_view);
            setFields(String.valueOf(office.getPhoneNumber()), phone_view);
            setFields(String.valueOf(office.getEmail()), email_view);
        }
        return view;
    }

    private void setButtonClick(TextView map_view, final String mapLink) {
        if (mapLink == null || mapLink.equals("")) {
            map_view.setVisibility(View.GONE);
            return;
        }
        map_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(mapLink));
                activity.startActivity(intent);
            }
        });
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
