package com.tech42.mari.inventorymanagement.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.adapter.ReceiptAdapter;
import com.tech42.mari.inventorymanagement.model.Receipt;
import com.tech42.mari.inventorymanagement.repository.ReceiptRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by mari on 1/24/17.
 */

public class ReceiptFragment extends Fragment implements View.OnClickListener {

    Realm realm;
    ReceiptAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton addbutton;
    ReceiptRepository controller;
    EditText textDoc, textDate, textComment;
    ArrayList<Receipt> latestresults = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_reciept, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.receiptlist);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab2);
        addbutton.setOnClickListener(this);
        controller = new ReceiptRepository(realm);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Receipt");
        controller.RetrievefromDB();
        latestresults = controller.Refresh();
        adapter = new ReceiptAdapter(getContext(), latestresults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_receipt, menu);
        if (menu != null) {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
            menu.removeItem(R.id.action_export);
            menu.removeItem(R.id.action_import);
            menu.removeItem(R.id.action_print);
            menu.removeItem(R.id.action_printmain);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == addbutton) {
            displayDialog();
        }
    }

    private void displayDialog() {

        final LayoutInflater inflator = LayoutInflater.from(getContext());
        View promptview = inflator.inflate(R.layout.dialog_create_document, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(promptview);
        dialog.setTitle("Create New Receipt");
        textDoc = (EditText) promptview.findViewById(R.id.txtDoc);
        textDate = (EditText) promptview.findViewById(R.id.txtDate);
        textComment = (EditText) promptview.findViewById(R.id.txtComment);
        String dates = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
        textDate.setText(dates);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SingleDateAndTimePickerDialog.Builder(getContext())
                        //.bottomSheet()
                        //.curved()
                        //.minutesStep(15)
                        .title("Select Date and Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                String dates = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
                                textDate.setText(dates);
                            }
                        }).display();
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
                Receipt receipt = new Receipt();
                receipt.setDocumentNumber(textDoc.getText().toString());
                receipt.setDate(textDate.getText().toString());
                receipt.setComments(textComment.getText().toString());
                receipt.setCode("");
                receipt.setName("");
                receipt.setCategory("");
                receipt.setQuantity(0);
                receipt.setUnit("");
                receipt.setTotal(0);
                receipt.setPrice(0);
                controller = new ReceiptRepository(realm);
                controller.save(receipt);
            }
        });
        dialog.show();
    }
}
