package com.shahzeb.a1.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class BuiltDates extends SugarRecord {

    private Long id;

    public Long getId() {
        return id;
    }

    public String key;
    public String value;

    public BuiltDates(){
    }

    public BuiltDates(String key, String value){
        this.key = key;
        this.value = value;
    }
}
