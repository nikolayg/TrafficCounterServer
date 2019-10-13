package com.nikgrozev.trafficcounter.db;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.nikgrozev.trafficcounter.model.VehicleRecord;

/**
 * Synchronised in-memory DB.
 * 
 * TODO: Replace with an actual db and perhaps some cache. 
 */
public class Db {
    private List<VehicleRecord> storage = new LinkedList<>();

    public synchronized void add(List<VehicleRecord> records) {
        storage.addAll(records);
    }

    public synchronized <QResult> QResult runQuery(Function<Stream<VehicleRecord>, QResult> query) {
        return query.apply(this.storage.stream());
    }
}