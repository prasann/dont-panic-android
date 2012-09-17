package com.thoughtworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.Country;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CountryListAdapter extends ArrayAdapter<Country> {
    private List<Country> items;
    private Context myContext;

    public CountryListAdapter(Context context, int textViewResourceId, List<Country> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) myContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.row_country, null);
        }
        Country country = items.get(position);
        if (country != null) {
            TextView id_view = (TextView) view.findViewById(R.id.row_country_id);
            TextView name_view = (TextView) view.findViewById(R.id.row_country_name);
            setFields(String.valueOf(country.getId()), id_view);
            setFields(country.getName(), name_view);
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
