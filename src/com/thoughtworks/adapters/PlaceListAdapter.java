package com.thoughtworks.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.Place;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PlaceListAdapter extends ArrayAdapter<Place> {
    private List<Place> items;
    private Context activity;
    private int layoutView;

    public PlaceListAdapter(Context context, int textViewResourceId, List<Place> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.activity = context;
        this.layoutView = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(layoutView, null);
        }
        Place place = items.get(position);
        if (place != null) {
            TextView name_view = (TextView) view.findViewById(R.id.row_office_name);
            TextView address_view = (TextView) view.findViewById(R.id.row_office_address);
            TextView phone_view = (TextView) view.findViewById(R.id.row_office_phone);
            TextView email_view = (TextView) view.findViewById(R.id.row_office_email);
            TextView map_view = (TextView) view.findViewById(R.id.row_office_map);
            setButtonClick(map_view, place.getMapLink());
            setFields(String.valueOf(place.getName()), name_view);
            setFields(String.valueOf(place.getAddress()), address_view);
            setFields(String.valueOf(place.getPhoneNumber()), phone_view);
            setFields(String.valueOf(place.getEmail()), email_view);
            if (place.getEmail() == null || place.getEmail().equals("")) {
                view.findViewById(R.id.off_mail_layout).setVisibility(View.GONE);
            }
            if (place.getPhoneNumber() == null || place.getPhoneNumber().equals("")) {
                view.findViewById(R.id.off_phone_layout).setVisibility(View.GONE);
            }
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
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
}
