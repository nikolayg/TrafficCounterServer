package com.nikgrozev.trafficcounter.job;

import java.util.TimerTask;
import com.nikgrozev.trafficcounter.db.Db;
import com.nikgrozev.trafficcounter.db.Queries;

/**
 * Synchronised in-memory DB.
 */
public class ReportTask extends TimerTask {
    private final Db db;

    public ReportTask(Db db) {
        this.db = db;
    }

    @Override
    public void run() {
        System.out.printf("[last 10min] numCars: %d, numCarsForBusiestSensor: %d, avgSpeedForFastestVehicleSize: %.2f\n", 
            db.runQuery(Queries::numCars),
            db.runQuery(Queries::numCarsForBusiestSensor),
            db.runQuery(Queries::avgSpeedForFastestVehicleSize)
        );
    }
}