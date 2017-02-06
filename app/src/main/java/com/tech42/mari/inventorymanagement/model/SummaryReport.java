package com.tech42.mari.inventorymanagement.model;

import io.realm.RealmObject;

/**
 * Created by mari on 2/2/17.
 */

public class SummaryReport extends RealmObject {

    private String code;

    private String name;

    private int in;

    private int invalue;

    private int out;

    private int outvalue;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getInvalue() {
        return invalue;
    }

    public void setInvalue(int invalue) {
        this.invalue = invalue;
    }

    public int getOutvalue() {
        return outvalue;
    }

    public void setOutvalue(int outvalue) {
        this.outvalue = outvalue;
    }
}
