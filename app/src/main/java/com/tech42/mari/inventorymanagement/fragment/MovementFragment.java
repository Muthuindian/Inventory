package com.tech42.mari.inventorymanagement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.adapter.MovementAdapter;
import com.tech42.mari.inventorymanagement.model.MovementReport;
import com.tech42.mari.inventorymanagement.repository.MovementRepository;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by mari on 1/24/17.
 */

public class MovementFragment extends Fragment {


    Realm realm;
    MovementRepository repository;
    MovementAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<MovementReport> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_movement_report, null);
        realm = Realm.getDefaultInstance();
        repository = new MovementRepository(realm);
        recyclerView = (RecyclerView) view.findViewById(R.id.movementlist);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Movement Report");
        list = repository.refresh();
        Log.e("Results" , list.toString());
        adapter = new MovementAdapter(getContext() , list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_report, menu);
        if (menu != null) {
            menu.removeItem(R.id.action_search);
            menu.removeItem(R.id.action_share);
            menu.removeItem(R.id.action_export);
            menu.removeItem(R.id.action_import);
            menu.removeItem(R.id.action_print);
            menu.removeItem(R.id.action_printmain);

        }
    }
}

