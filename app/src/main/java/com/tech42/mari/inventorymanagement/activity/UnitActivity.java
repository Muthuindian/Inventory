package com.tech42.mari.inventorymanagement.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.repository.UnitListRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mari on 2/1/17.
 */

public class UnitActivity extends AppCompatActivity {

    public ListView listView;
    private UnitListRepository repository;
    private List<String> unitlists = new ArrayList<>();
    private FloatingActionButton addbutton;
    private EditText textUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addunit);
        listView = (ListView) findViewById(R.id.unitlist);
        addbutton = (FloatingActionButton) findViewById(R.id.fab4);
        repository = new UnitListRepository(this);
        try {
            repository.createDataBase();
        } catch (IOException e) {
            Log.e("Exception" , e.toString());
        }
        repository.openDataBase();
        Cursor c = repository.getAllData();
        if (c != null && c.moveToFirst()) {
                do {
                    {
                        unitlists.add(c.getString(c.getColumnIndex("unit")));
                    }
                } while (c.getCount() != unitlists.size() - 1 && c.moveToNext());

        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, unitlists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final LayoutInflater inflator = LayoutInflater.from(UnitActivity.this);
                View promptview = inflator.inflate(R.layout.dialog_addunit, null);
                final String oldname = parent.getItemAtPosition(position).toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(UnitActivity.this);
                dialog.setView(promptview);
                dialog.setTitle("Edit Unit");
                textUnit = (EditText) promptview.findViewById(R.id.txtUnit);
                textUnit.setText(oldname);
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        repository.update(oldname , textUnit.getText().toString());
                        unitlists.set(position , textUnit.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
            });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflator = LayoutInflater.from(UnitActivity.this);
                View promptview = inflator.inflate(R.layout.dialog_addunit, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(UnitActivity.this);
                dialog.setView(promptview);
                dialog.setTitle("Create New Unit");
                textUnit = (EditText) promptview.findViewById(R.id.txtUnit);
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        repository.insert(textUnit.getText().toString());
                        unitlists.add(textUnit.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
    }
}
