package com.tech42.mari.inventorymanagement.Management;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.tech42.mari.inventorymanagement.Adapters.RecyclerViewAdapter;
import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.Realm.RealmController;
import com.tech42.mari.inventorymanagement.RealmModel.Inventory;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by mari on 1/24/17.
 */

public class Activity_Inventory extends Fragment implements View.OnClickListener{

    Realm realm;
    ArrayList<Inventory> inventories;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton addbutton;
    RealmController controller;
    EditText textcode , textname , textcategory;
    Spinner textcat , textunit;
    String[] categorylist = {"General"};
    String[] unit = {"cm" , "kg" , "pcs"};



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.inventory , null);
        recyclerView = (RecyclerView) view.findViewById(R.id.inventorylist);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab1);
        addbutton.setOnClickListener(this);
        controller= new RealmController(realm);
        inventories = controller.Refresh();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Inventory");
        //controller.RetrievefromDB();
        adapter = new RecyclerViewAdapter(this , inventories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inventory_menu, menu);
        if(menu!=null)
        {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == addbutton)
        {
            displayDialog();
        }

    }

    private void displayDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(R.layout.inventorydialog);
        dialog.setTitle("Create New Item");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Inventory inventory = new Inventory();
            }
        });



        dialog.show();
    }
}
