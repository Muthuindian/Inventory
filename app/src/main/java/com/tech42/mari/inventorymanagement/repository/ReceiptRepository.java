package com.tech42.mari.inventorymanagement.repository;

import com.tech42.mari.inventorymanagement.model.Receipt;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by mari on 1/28/17.
 */

public class ReceiptRepository {
    Realm realm;
    RealmResults<Receipt> realmResults;
    Boolean saved = null;

    public ReceiptRepository(Realm realm) {
        this.realm = realm;
    }

    public boolean save(final Receipt receipt) {
        if (receipt == null) {
            saved = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        Receipt receiptobject = realm.copyToRealm(receipt);
                        saved = true;
                    } catch (RealmException ex) {
                        ex.printStackTrace();
                        saved = false;
                    }
                }
            });
        }
        return saved;
    }

    public void RetrievefromDB() {
        realmResults = realm.where(Receipt.class).findAll();
    }

    public ArrayList<Receipt> Refresh() {
        ArrayList<Receipt> latestresults = new ArrayList<>();
        realmResults = realm.where(Receipt.class).findAll();
        for (Receipt i : realmResults) {
            latestresults.add(i);
        }
        return latestresults;
    }
}
