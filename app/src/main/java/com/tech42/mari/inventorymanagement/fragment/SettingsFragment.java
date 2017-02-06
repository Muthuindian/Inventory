package com.tech42.mari.inventorymanagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.activity.CategoryActivity;
import com.tech42.mari.inventorymanagement.activity.UnitActivity;
import com.tech42.mari.inventorymanagement.adapter.SettingsAdapter;

/**
 * Created by mari on 1/24/17.
 */

public class SettingsFragment extends ListFragment {
    
    private int[] imageids = {R.mipmap.backup, R.mipmap.restore, R.mipmap.unit, R.mipmap.notification, R.mipmap.category, R.mipmap.remove_ads, R.mipmap.about, R.mipmap.rate};
    private String[] descriptions = {"Backup", "Restore", "Unit", "Stock Notification", "Category", "Remove ads", "About us", "Rate us"};
    private SettingsAdapter  adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Settings");
        getListView();
        adapter = new SettingsAdapter(getActivity(), imageids, descriptions);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Context context = v.getContext();
        Intent intent;
        if (l.getItemAtPosition(position).toString().equals("Unit")) {
            intent = new Intent(v.getContext(), UnitActivity.class);
            context.startActivity(intent);
        }
        if (l.getItemAtPosition(position).toString().equals("Category")) {
            intent = new Intent(v.getContext(), CategoryActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_report, menu);
        if (menu != null) {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
            menu.removeItem(R.id.action_export);
            menu.removeItem(R.id.action_import);
            menu.removeItem(R.id.action_print);
            menu.removeItem(R.id.action_printmain);
        }
    }
}

