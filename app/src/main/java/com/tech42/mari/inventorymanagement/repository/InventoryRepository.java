package com.tech42.mari.inventorymanagement.repository;


import com.tech42.mari.inventorymanagement.model.Inventory;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by mari on 1/27/17.
 */

public class InventoryRepository {

    Realm realm;
    RealmResults<Inventory> realmResults;
    Boolean saved = null;

    public InventoryRepository(Realm realm) {
        this.realm = realm;
    }

    public boolean save(final Inventory inventory) {
        if (inventory == null) {
            saved = false;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        Inventory inventoryobject = realm.copyToRealm(inventory);
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

    public void retrieveFromDB() {
        realmResults = realm.where(Inventory.class).findAll();
    }

    public ArrayList<Inventory> Refresh() {
        ArrayList<Inventory> latestresults = new ArrayList<>();
        realmResults = realm.where(Inventory.class).findAll();
        for (Inventory i : realmResults) {
            latestresults.add(i);
        }
        return latestresults;
    }
}
