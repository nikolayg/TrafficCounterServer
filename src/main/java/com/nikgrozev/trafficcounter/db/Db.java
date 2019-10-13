package com.nikgrozev.trafficcounter.db;

import java.util.ArrayList;
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
    private List<VehicleRecord> storage = new ArrayList<>();

    public synchronized void add(List<VehicleRecord> records) {
        storage.addAll(records);
    }

    /**
     * Executes a query. Every query is a function which takes Stream<VehicleRecord> as a parameter.
     *  
     * @param <QResult> - the result type of the query.
     * @param query - the query function.
     * @return the result of the query execution on the database.
     */
    public synchronized <QResult> QResult runQuery(Function<Stream<VehicleRecord>, QResult> query) {
        return query.apply(this.storage.stream());
    }
}