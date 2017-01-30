package com.tech42.mari.inventorymanagement.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by mari on 1/25/17.
 */

public class Inventory extends RealmObject {

    @PrimaryKey
    private String code;

    @Required
    private String name;

    @Required
    private String category;

    private int quantity;

    private double totalvalue;


    private String unit;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(double totalvalue) {
        this.totalvalue = totalvalue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
