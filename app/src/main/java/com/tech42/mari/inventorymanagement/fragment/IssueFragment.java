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
import com.tech42.mari.inventorymanagement.adapter.IssueAdapter;
import com.tech42.mari.inventorymanagement.model.Issue;
import com.tech42.mari.inventorymanagement.repository.IssueRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by mari on 1/24/17.
 */

public class IssueFragment extends Fragment implements View.OnClickListener {
    private Realm realm;
    private IssueAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addbutton;
    private IssueRepository controller;
    private EditText textDoc, textDate, textComment;
    private ArrayList<Issue> latestresults = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_issue, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.issuelist);
        addbutton = (FloatingActionButton) view.findViewById(R.id.fab3);
        addbutton.setOnClickListener(this);
        controller = new IssueRepository(realm);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Issue");
        latestresults = controller.refresh();
        adapter = new IssueAdapter(getContext(), latestresults);
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

        if (v.equals(addbutton)) {
            displayDialog();
        }
    }

    private void displayDialog() {

        final LayoutInflater inflator = LayoutInflater.from(getContext());
        View promptview = inflator.inflate(R.layout.dialog_create_document, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setView(promptview);
        dialog.setTitle("Create New Issue");
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
                Issue issue = new Issue();
                issue.setDocumentNumber(textDoc.getText().toString());
                issue.setDate(textDate.getText().toString());
                issue.setComments(textComment.getText().toString());
                issue.setCode("");
                issue.setName("");
                issue.setCategory("");
                issue.setQuantity(0);
                issue.setUnit("");
                issue.setTotal(0);
                issue.setPrice(0);
                controller = new IssueRepository(realm);
                controller.save(issue);
                latestresults = controller.refresh();
                adapter = new IssueAdapter(getContext(), latestresults);
                recyclerView.setAdapter(adapter);
            }
        });
        dialog.show();
    }
}
