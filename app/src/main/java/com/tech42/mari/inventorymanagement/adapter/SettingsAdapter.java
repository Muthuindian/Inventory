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

    private Activity context;
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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_settings, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.settingsicon);
        TextView text = (TextView) convertView.findViewById(R.id.description);
        image.setImageResource(imageId[position]);
        text.setText(description[position]);
        return convertView;
    }
}
