package com.shahzeb.a1.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Recents extends SugarRecord{

    private Long id;

    public Long getId() {
        return id;
    }

    public String manufacturer;
    public String mainType;
    public String builtDate;

    public Recents(){}

    public Recents(String manufacturer, String mainType, String builtDate) {
        this.manufacturer = manufacturer;
        this.mainType = mainType;
        this.builtDate = builtDate;
    }
}
