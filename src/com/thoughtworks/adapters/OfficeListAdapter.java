package com.thoughtworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.City;
import com.thoughtworks.models.Office;
import com.thoughtworks.models.Place;
import com.thoughtworks.viewmodels.OfficeInfo;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OfficeListAdapter extends ArrayAdapter<OfficeInfo> {
    private List<OfficeInfo> items;
    private Context myContext;
    private int layoutView;

    public OfficeListAdapter(Context context, int textViewResourceId, List<OfficeInfo> items) {
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
        OfficeInfo office = items.get(position);
        if (office != null) {
            TextView id_view = (TextView) view.findViewById(R.id.row_office_id);
            TextView name_view = (TextView) view.findViewById(R.id.row_office_name);
            TextView comp_name_view = (TextView) view.findViewById(R.id.row_company_name);
            setFields(String.valueOf(office.getOfficeId()), id_view);
            setFields(office.getOfficeName(), name_view);
            setFields(office.getCompanyName(), comp_name_view);
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
