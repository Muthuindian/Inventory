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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tech42.mari.inventorymanagement.Adapters.RecyclerViewAdapter;
import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.Realm.RealmController;
import com.tech42.mari.inventorymanagement.RealmModel.Inventory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by mari on 1/24/17.
 */

public class Activity_Inventory extends Fragment implements View.OnClickListener{

    Realm realm;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton addbutton;
    RealmController controller;
    EditText textcode , textname;
    Spinner textcat , textunit;
    String[] categorylist = new String[]{"General"};
    String[] unitlist = new String[]{"cm" , "kg" , "pcs"};
    ArrayList<Inventory> latestresults=new ArrayList<>();
    RelativeLayout topview;
    TextView currentdate , stockvalue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.inventory , null);
        topview = (RelativeLayout) view.findViewById(R.id.top);
        currentdate = (TextView) view.findViewById(R.id.datetime);
        stockvalue = (TextView) view.findViewById(R.id.stockvalue);
        topview.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.inventorylist);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab1);
        addbutton.setOnClickListener(this);
        controller= new RealmController(realm);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Inventory");
        String date;
        Date cal = Calendar.getInstance().getTime();
        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cal);
        currentdate.setText(date);
        controller.RetrievefromDB();
        latestresults = controller.Refresh();
        /*if(!latestresults.isEmpty())
            Toast.makeText(getContext() , "Result" + latestresults.get(0) , Toast.LENGTH_SHORT).show();
        */adapter = new RecyclerViewAdapter(getContext() , latestresults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        if(v == topview)
        {

        }

    }

    private void displayDialog() {

        final LayoutInflater inflator = LayoutInflater.from(getContext());
        View promptview = inflator.inflate(R.layout.inventorydialog , null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(promptview);
        dialog.setTitle("Create New Item");
        textcat = (Spinner) promptview.findViewById(R.id.spCategory);
        textunit = (Spinner) promptview.findViewById(R.id.spUnit);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext() , android.R.layout.simple_spinner_item , categorylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textcat.setAdapter(adapter);
        adapter = new ArrayAdapter(getContext() , android.R.layout.simple_spinner_item , unitlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textunit.setAdapter(adapter);
        textcat.setSelection(0);
        textunit.setSelection(0);
        textcode = (EditText) promptview.findViewById(R.id.txtCode);
        textname = (EditText) promptview.findViewById(R.id.txtName);
        dialog.setNegativeButton("CANCEL" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Inventory inventory = new Inventory();
                inventory.setCode(textcode.getText().toString());
                inventory.setName(textname.getText().toString());
                inventory.setCategory(textcat.getSelectedItem().toString());
                inventory.setUnit(textunit.getSelectedItem().toString());
                controller = new RealmController(realm);
                controller.save(inventory);
            }
        });
        dialog.show();
    }
}
