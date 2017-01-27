package com.tech42.mari.inventorymanagement.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by mari on 1/25/17.
 */

public class Category extends RealmObject {

    @PrimaryKey
    private String code;

    @Required
    private String name;

    @Required
    private String category;


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
}
