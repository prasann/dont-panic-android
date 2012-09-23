package com.thoughtworks.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thoughtworks.R;
import com.thoughtworks.models.PlaceType;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<PlaceType> placeTypes;

    public ImageAdapter(Context c, List<PlaceType> placeTypes) {
        mContext = c;
        this.placeTypes = placeTypes;
    }

    public int getCount() {
        return placeTypes.size() + 3;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            switch (position) {
                case 0:
                    v = setImageText("Favourites", R.drawable.favourites);
                    break;
                case 1:
                    v = setImageText("Offices", R.drawable.offices);
                    break;
                case 2:
                    v = setImageText("Admins", R.drawable.admins);
                    break;
                default:
                    String name = placeTypes.get(position - 3).getName();
                    v = setImageText(name, getResources(name));
            }
        } else {
            v = convertView;
        }
        return v;
    }

    private int getResources(String name) {
        int resId = mContext.getResources().getIdentifier(name.toLowerCase(), "drawable", "com.thoughtworks");
        if (resId == 0) {
            resId = mContext.getResources().getIdentifier("building", "drawable", "com.thoughtworks");
        }
        return resId;
    }

    private View setImageText(String text, Integer imageResource) {
        View v;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.grid_item, null);
        TextView tv = (TextView) v.findViewById(R.id.icon_text);
        tv.setText(text);
        ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
        iv.setImageResource(imageResource);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return v;
    }
}