package com.thoughtworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Country;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CityListAdapter extends ArrayAdapter<City> {
    private List<City> items;
    private Context myContext;
    private int layoutView;

    public CityListAdapter(Context context, int textViewResourceId, List<City> items) {
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
        City city = items.get(position);
        if (city != null) {
            TextView id_view = (TextView) view.findViewById(R.id.row_city_id);
            TextView name_view = (TextView) view.findViewById(R.id.row_city_name);
            setFields(String.valueOf(city.getId()), id_view);
            setFields(city.getName(), name_view);
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
