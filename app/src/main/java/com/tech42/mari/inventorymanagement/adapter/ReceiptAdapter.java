package com.tech42.mari.inventorymanagement.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.model.Inventory;
import com.tech42.mari.inventorymanagement.model.Receipt;
import com.tech42.mari.inventorymanagement.repository.ReceiptRepository;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mari on 1/28/17.
 */

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {
    ArrayList<Receipt> recipts;
    Context mycontext;

    public ReceiptAdapter(Context context, ArrayList<Receipt> receipts) {
        this.mycontext = context;
        this.recipts = receipts;
    }

    @Override
    public ReceiptAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_receipt, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptAdapter.MyViewHolder holder, int position) {

        Receipt receiptobject = recipts.get(position);
        holder.docno.setText(receiptobject.getDocumentNumber());
        holder.date.setText(receiptobject.getDate());
        holder.comment.setText(receiptobject.getComments());
        holder.total.setText((int) receiptobject.getTotal());
    }

    @Override
    public int getItemCount() {
        return recipts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView docno, date, comment, total;
        String[] categorylist = new String[]{"General"};
        String[] unitlist = new String[]{"cm", "kg", "pcs"};
        Spinner category, unit;
        EditText code, name, qty, price;
        Realm realm = Realm.getDefaultInstance();
        ReceiptRepository controller;

        public MyViewHolder(View itemView) {
            super(itemView);
            docno = (TextView) itemView.findViewById(R.id.docnotext);
            date = (TextView) itemView.findViewById(R.id.datetext);
            comment = (TextView) itemView.findViewById(R.id.commenttext);
            total = (TextView) itemView.findViewById(R.id.total1text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            final LayoutInflater inflator = LayoutInflater.from(mycontext);
            View promptview = inflator.inflate(R.layout.dialog_receipt_item, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(mycontext);
            dialog.setView(promptview);
            dialog.setTitle("Create New Item");
            category = (Spinner) promptview.findViewById(R.id.spCategory);
            unit = (Spinner) promptview.findViewById(R.id.spUnit);
            ArrayAdapter<String> adapter = new ArrayAdapter(mycontext, android.R.layout.simple_spinner_item, categorylist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(adapter);
            adapter = new ArrayAdapter(mycontext, android.R.layout.simple_spinner_item, unitlist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unit.setAdapter(adapter);
            category.setSelection(0);
            unit.setSelection(0);
            code = (EditText) promptview.findViewById(R.id.txtCode);
            name = (EditText) promptview.findViewById(R.id.txtName);
            qty = (EditText) promptview.findViewById(R.id.txtQuantity);
            price = (EditText) promptview.findViewById(R.id.txtPrice);
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    realm = Realm.getDefaultInstance();
                    controller = new ReceiptRepository(realm);
                    realm.beginTransaction();
                    RealmResults<Receipt> results = realm.where(Receipt.class).findAll();
                    Log.v("RESULT", results.toString());
                    results.get(getPosition()).setCode(code.getText().toString());
                    results.get(getPosition()).setName(name.getText().toString());
                    results.get(getPosition()).setCategory(category.getSelectedItem().toString());
                    results.get(getPosition()).setUnit(unit.getSelectedItem().toString());
                    results.get(getPosition()).setQuantity(Integer.parseInt(qty.getText().toString()));
                    results.get(getPosition()).setPrice(Integer.parseInt(price.getText().toString()));
                    results.get(getPosition()).setTotal(Integer.parseInt(qty.getText().toString()) * Integer.parseInt(price.getText().toString()));
                    realm.commitTransaction();
                    Log.v("RESULT", results.toString());
                    realm.beginTransaction();
                    Inventory result = realm.where(Inventory.class).equalTo("code", code.getText().toString()).findFirst();
                    Log.v("RESULT", results.toString());
                    result.setQuantity(Integer.parseInt(qty.getText().toString()));
                    result.setTotalvalue(Integer.parseInt(qty.getText().toString()) * Integer.parseInt(price.getText().toString()));
                    realm.commitTransaction();
                    Log.v("RESULT", result.toString());
                }
            });
            dialog.show();
        }
    }
}

