package com.tech42.mari.inventorymanagement.model;

import io.realm.RealmObject;

/**
 * Created by mari on 2/2/17.
 */

public class MovementReport extends RealmObject {

    private String date;

    private String code;

    private String name;

    private String in;

    private String out;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
