package com.tech42.mari.inventorymanagement.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;

/**
 * Created by mari on 1/25/17.
 */

public class SettingsAdapter extends ArrayAdapter {

    Activity context;
    private int[] imageId;
    private String[] description;

    public SettingsAdapter(Activity context, int[] imageId, String[] description) {
        super(context, R.layout.item_settings, description);
        this.context = context;
        this.imageId = imageId;
        this.description = description;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_settings, null, true);
        ImageView image = (ImageView) rowView.findViewById(R.id.settingsicon);
        TextView text = (TextView) rowView.findViewById(R.id.description);
        image.setImageResource(imageId[position]);
        text.setText(description[position]);
        return rowView;
    }
}
