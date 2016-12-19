package com.shahzeb.a1.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class MainType extends SugarRecord {
    private Long id;

    public Long getId() {
        return id;
    }

    public String key;
    public String value;

    public MainType(){
    }

    public MainType(String key, String value){
        this.key = key;
        this.value = value;
    }
}
