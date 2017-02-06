package com.tech42.mari.inventorymanagement.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.model.Category;
import com.tech42.mari.inventorymanagement.model.Inventory;
import com.tech42.mari.inventorymanagement.model.MovementReport;
import com.tech42.mari.inventorymanagement.model.Receipt;
import com.tech42.mari.inventorymanagement.model.SummaryReport;
import com.tech42.mari.inventorymanagement.repository.MovementRepository;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mari on 1/28/17.
 */

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {
    private ArrayList<Receipt> recipts;
    private Context mycontext;

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
        if (receiptobject.getTotal() == 0) {
            holder.total.setText("0");
        } else {
            Log.e("Total" , String.valueOf(receiptobject.getTotal()));
            holder.total.setText(String.valueOf(receiptobject.getTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return recipts.size();
    }


    /**
     * Created by mari on 1/27/17.
     */

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView docno, date, comment, total;
        private String[] categorylist;
        private String[] unitlist;
        private Spinner category, unit;
        private EditText code, name, qty, price;
        private Realm realm = Realm.getDefaultInstance();

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
            View promptview = inflator.inflate(R.layout.dialog_receipt_issue_item, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(mycontext);
            dialog.setView(promptview);
            dialog.setTitle("Create New Item");
            category = (Spinner) promptview.findViewById(R.id.spCategory);
            unit = (Spinner) promptview.findViewById(R.id.spUnit);
            code = (EditText) promptview.findViewById(R.id.txtCode);
            name = (EditText) promptview.findViewById(R.id.txtName);
            qty = (EditText) promptview.findViewById(R.id.txtQuantity);
            price = (EditText) promptview.findViewById(R.id.txtPrice);
            code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!code.getText().toString().isEmpty()) {
                        realm.beginTransaction();
                        Category result = realm.where(Category.class).equalTo("code", code.getText().toString()).findFirst();
                        categorylist = new String[]{result.getCategoryname()};
                        unitlist = new String[]{result.getUnit()};
                        ArrayAdapter<String> adapter = new ArrayAdapter(mycontext, android.R.layout.simple_spinner_item, categorylist);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        category.setAdapter(adapter);
                        adapter = new ArrayAdapter(mycontext, android.R.layout.simple_spinner_item, unitlist);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        unit.setAdapter(adapter);
                        category.setSelection(0);
                        unit.setSelection(0);
                        name.setText(result.getName());
                        name.setEnabled(false);
                        category.setEnabled(false);
                        unit.setEnabled(false);
                        realm.commitTransaction();
                    }
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //realm = Realm.getDefaultInstance();
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
                    double updated = Integer.parseInt(qty.getText().toString()) * Integer.parseInt(price.getText().toString());
                    result.setTotalvalue(result.getTotalvalue() + updated);
                    realm.commitTransaction();
                    MovementReport movementReport = new MovementReport();
                    String date = results.get(getPosition()).getDate().substring(0 , 10);
                    movementReport.setDate(date);
                    movementReport.setName(name.getText().toString());
                    movementReport.setCode(code.getText().toString());
                    movementReport.setIn(Integer.parseInt(qty.getText().toString()) + unit.getSelectedItem().toString());
                    movementReport.setOut("0" + unit.getSelectedItem().toString());
                    MovementRepository repository = new MovementRepository(realm);
                    repository.save(movementReport);
                    realm.beginTransaction();
                    SummaryReport summaryReport = realm.where(SummaryReport.class).equalTo("code" , code.getText().toString()).findFirst();
                    Log.e("IN Vaue" , String.valueOf(summaryReport.getInvalue()));
                    summaryReport.setIn(summaryReport.getIn() + Integer.parseInt(qty.getText().toString()));
                    summaryReport.setInvalue(summaryReport.getInvalue() + (Integer.parseInt(qty.getText().toString()) * Integer.parseInt(price.getText().toString())));
                    realm.commitTransaction();
                }
            });
            dialog.show();
        }
    }
}

