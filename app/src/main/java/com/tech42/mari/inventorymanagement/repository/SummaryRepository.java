package com.tech42.mari.inventorymanagement.repository;

import android.util.Log;

import com.tech42.mari.inventorymanagement.model.SummaryReport;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by mari on 2/2/17.
 */

public class SummaryRepository {

    private Realm realm;
    private RealmResults<SummaryReport> realmResults;
    private Boolean saved = null;

    public SummaryRepository(Realm realm) {
        this.realm = realm;
    }

    public boolean save(final SummaryReport report) {
        if (report == null) {
            saved = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        SummaryReport reportobject = realm.copyToRealm(report);
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

    public ArrayList<SummaryReport> refresh() {
        ArrayList<SummaryReport> latestresults = new ArrayList<>();
        realmResults = realm.where(SummaryReport.class).findAll();
        for (SummaryReport i : realmResults) {
            latestresults.add(i);
        }
        return latestresults;
    }
}
