package com.thoughtworks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.Administrator;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AdminListAdapter extends ArrayAdapter<Administrator> {
    private List<Administrator> items;
    private Context myContext;
    private int layoutView;

    public AdminListAdapter(Context context, int textViewResourceId, List<Administrator> items) {
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
        Administrator admin = items.get(position);
        if (admin != null) {
            TextView name_view = (TextView) view.findViewById(R.id.row_admin_name);
            TextView phone_view = (TextView) view.findViewById(R.id.row_admin_number);
            TextView email_view = (TextView) view.findViewById(R.id.row_admin_email);
            setFields(String.valueOf(admin.getName()), name_view);
            setFields(String.valueOf(admin.getPhoneNumbers()), phone_view);
            setFields(String.valueOf(admin.getEmail()), email_view);
        }
        return view;
    }

    private void setFields(String text, TextView textView) {
        if (textView != null) {
            textView.setText(text);
        }
    }
}
