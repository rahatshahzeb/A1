package com.shahzeb.a1.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Manufacturer extends SugarRecord {

    private Long id;

    public String key;
    public String value;

    public Manufacturer(){
    }

    public Manufacturer(String key, String value){
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }
}
