package com.tech42.mari.inventorymanagement.Reports;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech42.mari.inventorymanagement.R;

/**
 * Created by mari on 1/24/17.
 */

public class Activity_Movement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.movementreport , null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Movement Report");
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

