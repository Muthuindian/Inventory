package com.tech42.mari.inventorymanagement.repository;

import android.util.Log;

import com.tech42.mari.inventorymanagement.model.MovementReport;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by mari on 2/2/17.
 */

public class MovementRepository {

    private Realm realm;
    private RealmResults<MovementReport> realmResults;
    private Boolean saved = null;

    public MovementRepository(Realm realm) {
        this.realm = realm;
    }

    public boolean save(final MovementReport report) {
        if (report == null) {
            saved = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        MovementReport reportobject = realm.copyToRealm(report);
                        saved = true;
                    } catch (RealmException ex) {
                        Log.e("Exception" , ex.toString());
                        saved = false;
                    }
                }
            });
        }
        return saved;
    }

    public ArrayList<MovementReport> refresh() {
        ArrayList<MovementReport> latestresults = new ArrayList<>();
        realmResults = realm.where(MovementReport.class).findAll();
        for (MovementReport i : realmResults) {
            latestresults.add(i);
        }
        return latestresults;
    }


}
