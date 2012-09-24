package com.thoughtworks.adapters;

import android.content.Context;
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
    private Context myContext;
    private int layoutView;

    public PlaceListAdapter(Context context, int textViewResourceId, List<Place> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.myContext = context;
        this.layoutView = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) myContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(layoutView, null);
        }
        Place office = items.get(position);
        if (office != null) {
            TextView name_view = (TextView) view.findViewById(R.id.row_office_name);
            TextView address_view = (TextView) view.findViewById(R.id.row_office_address);
            TextView phone_view = (TextView) view.findViewById(R.id.row_office_phone);
            TextView email_view = (TextView) view.findViewById(R.id.row_office_email);
            setFields(String.valueOf(office.getName()), name_view);
            setFields(String.valueOf(office.getAddress()), address_view);
            setFields(String.valueOf(office.getPhoneNumber()), phone_view);
            setFields(String.valueOf(office.getEmail()), email_view);
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
