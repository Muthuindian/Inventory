package com.tech42.mari.inventorymanagement.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.adapter.InventoryAdapter;
import com.tech42.mari.inventorymanagement.model.Category;
import com.tech42.mari.inventorymanagement.model.Inventory;
import com.tech42.mari.inventorymanagement.model.SummaryReport;
import com.tech42.mari.inventorymanagement.repository.CategoryListRepository;
import com.tech42.mari.inventorymanagement.repository.CategoryRepository;
import com.tech42.mari.inventorymanagement.repository.InventoryRepository;
import com.tech42.mari.inventorymanagement.repository.SummaryRepository;
import com.tech42.mari.inventorymanagement.repository.UnitListRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by mari on 1/24/17.
 */

public class InventoryFragment extends Fragment implements View.OnClickListener {

    private Realm realm;
    public static InventoryAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addbutton;
    private InventoryRepository controller;
    private EditText textcode, textname;
    private Spinner textcat, textunit;
    private String[] categorylist;
    private String[] unitlist;
    private UnitListRepository unitListRepository;
    private CategoryListRepository categoryListRepository;
    private ArrayList<Inventory> latestresults = new ArrayList<>();
    private RelativeLayout topview;
    private TextView currentdate;
    private static int i;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
        unitListRepository = new UnitListRepository(getContext());
        i = 0;
        try {
            unitListRepository.createDataBase();
        } catch (IOException e) {
            Log.e("Exception" , e.toString());
        }
        unitListRepository.openDataBase();
        Cursor data = unitListRepository.getAllData();
        if (data != null) {
            Toast.makeText(getActivity() , "No of Rows" + data.getCount() ,  Toast.LENGTH_SHORT).show();
            unitlist = new String[data.getCount()];
            if (data.moveToFirst()) {
                do {
                    {
                        unitlist[i] = data.getString(data.getColumnIndex("unit"));
                        i++;
                    }
                } while (data.getCount() != i && data.moveToNext());
            }
        }
        categoryListRepository = new CategoryListRepository(getContext());
        i = 0;
        try {
            categoryListRepository.createDataBase();
        } catch (IOException e) {
            Log.e("Exception" , e.toString());
        }
        categoryListRepository.openDataBase();
        Cursor allData = categoryListRepository.getAllData();
        if (allData != null) {
            Toast.makeText(getActivity() , "No of Rows" + allData.getCount() ,  Toast.LENGTH_SHORT).show();
            categorylist = new String[allData.getCount()];
            if (allData.moveToFirst()) {
                do {
                    {
                        categorylist[i] = allData.getString(allData.getColumnIndex("category"));
                        i++;
                    }
                } while (allData.getCount() != i && allData.moveToNext());
            }
        }
        View view = inflater.inflate(R.layout.fragment_inventory, null);
        topview = (RelativeLayout) view.findViewById(R.id.top);
        currentdate = (TextView) view.findViewById(R.id.datetime);
        topview.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.inventorylist);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab1);
        addbutton.setOnClickListener(this);
        controller = new InventoryRepository(realm);
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
        latestresults = controller.refresh();
        adapter = new InventoryAdapter(getContext(), latestresults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_inventory, menu);
        if (menu != null) {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(addbutton)) {
            displayDialog();
        }
        if (v.equals(topview)) {
            new SingleDateAndTimePickerDialog.Builder(getContext())
                    //.bottomSheet()
                    //.curved()
                    //.minutesStep(15)
                    .title("Select Date and Time")
                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                        @Override
                        public void onDateSelected(Date date) {
                            String dates = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
                            currentdate.setText(dates);
                        }
                    }).display();
        }
    }

    private void displayDialog() {

        final LayoutInflater inflator = LayoutInflater.from(getContext());
        View promptview = inflator.inflate(R.layout.dialog_inventory_item, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(promptview);
        dialog.setTitle("Create New Item");
        textcat = (Spinner) promptview.findViewById(R.id.spCategory);
        textunit = (Spinner) promptview.findViewById(R.id.spUnit);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categorylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textcat.setAdapter(adapter);
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, unitlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textunit.setAdapter(adapter);
        textcat.setSelection(0);
        textunit.setSelection(0);
        textcode = (EditText) promptview.findViewById(R.id.txtCode);
        textname = (EditText) promptview.findViewById(R.id.txtName);
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Inventory inventory = new Inventory();
                inventory.setCode(textcode.getText().toString());
                inventory.setName(textname.getText().toString());
                inventory.setCategory(textcat.getSelectedItem().toString());
                inventory.setUnit(textunit.getSelectedItem().toString());
                inventory.setQuantity(0);
                inventory.setTotalvalue(0);
                controller = new InventoryRepository(realm);
                controller.save(inventory);
                Category category = new Category();
                category.setCode(textcode.getText().toString());
                category.setName(textname.getText().toString());
                category.setCategoryname(textcat.getSelectedItem().toString());
                category.setUnit(textunit.getSelectedItem().toString());
                CategoryRepository repository = new CategoryRepository(realm);
                repository.save(category);
                SummaryReport summaryReport = new SummaryReport();
                summaryReport.setCode(textcode.getText().toString());
                summaryReport.setName(textname.getText().toString());
                summaryReport.setIn(0);
                summaryReport.setOut(0);
                summaryReport.setOutvalue(0);
                summaryReport.setInvalue(0);
                SummaryRepository srepository = new SummaryRepository(realm);
                srepository.save(summaryReport);
                latestresults = controller.refresh();
                InventoryFragment.adapter = new InventoryAdapter(getContext() , latestresults);
                recyclerView.setAdapter(InventoryFragment.adapter);
            }
        });
        dialog.show();
    }
}
