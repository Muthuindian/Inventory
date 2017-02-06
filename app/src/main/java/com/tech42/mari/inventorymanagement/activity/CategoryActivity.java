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
import com.tech42.mari.inventorymanagement.repository.CategoryListRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mari on 2/1/17.
 */

public class CategoryActivity extends AppCompatActivity {

    public ListView listView;
    private CategoryListRepository repository;
    private List<String> categorylist = new ArrayList<>();
    private FloatingActionButton addbutton;
    private EditText textCategory;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
        listView = (ListView) findViewById(R.id.categorylist);
        addbutton = (FloatingActionButton) findViewById(R.id.fab5);
        repository = new CategoryListRepository(this);
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
                        categorylist.add(c.getString(c.getColumnIndex("category")));
                    }
                } while (c.getCount() != categorylist.size() - 1 && c.moveToNext());

        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, categorylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final LayoutInflater inflator = LayoutInflater.from(CategoryActivity.this);
                View promptview = inflator.inflate(R.layout.dialog_addcategory, null);
                final String oldname = parent.getItemAtPosition(position).toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(CategoryActivity.this);
                dialog.setView(promptview);
                dialog.setTitle("Edit Category");
                textCategory = (EditText) promptview.findViewById(R.id.txtCategory);
                textCategory.setText(oldname);
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        repository.update(oldname , textCategory.getText().toString());
                        categorylist.set(position , textCategory.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflator = LayoutInflater.from(CategoryActivity.this);
                View promptview = inflator.inflate(R.layout.dialog_addcategory, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(CategoryActivity.this);
                dialog.setView(promptview);
                dialog.setTitle("Create New Category");
                textCategory = (EditText) promptview.findViewById(R.id.txtCategory);
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        repository.insert(textCategory.getText().toString());
                        categorylist.add(textCategory.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
    }
}
