package com.sunikita.bbapp.content;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by StarLord on 2016-11-12.
 */

public class ExActivityRecord {

    private Date entryDate;
    private List<Pair<Float, Integer>> records = new ArrayList<>();

    public ExActivityRecord(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void addRecord(Float weight, Integer repetitions) {
        this.records.add(new Pair<>(weight, repetitions));
    }

    public Date getEntryDate() {
        return new Date(this.entryDate.getTime());
    }

    public List<Pair<Float, Integer>> getRecords(){
        return this.records;
    }
}
