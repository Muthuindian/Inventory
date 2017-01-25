package com.tech42.mari.inventorymanagement.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tech42.mari.inventorymanagement.Adapters.SettingsAdapter;
import com.tech42.mari.inventorymanagement.R;

/**
 * Created by mari on 1/24/17.
 */

public class Activity_Settings extends ListFragment {

    private int[] imageids={R.mipmap.backup , R.mipmap.restore , R.mipmap.unit , R.mipmap.notification , R.mipmap.category , R.mipmap.remove_ads , R.mipmap.about , R.mipmap.rate};
    private  String[] descriptions = { "Backup" , "Restore" , "Unit" , "Stock Notification" , "Category" , "Remove ads" , "About us" , "Rate us"};
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.settings , null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Settings");
        getListView();
        setListAdapter(new SettingsAdapter(getActivity() , imageids , descriptions));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.e("CLICK" , "item"+id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.report_menu , menu);
        if(menu!=null)
        {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
            menu.removeItem(R.id.action_export);
            menu.removeItem(R.id.action_import);
            menu.removeItem(R.id.action_print);
            menu.removeItem(R.id.action_printmain);

        }
    }
}

