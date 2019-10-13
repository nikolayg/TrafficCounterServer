package com.nikgrozev.trafficcounter.db;

import static org.junit.Assert.*;

import java.util.stream.Stream;
import org.junit.Test;
import com.nikgrozev.trafficcounter.model.VehicleRecord;
import com.nikgrozev.trafficcounter.model.VehicleSize;

public class QueriesTest {

    private static long before(int m) {
        return System.currentTimeMillis() - (m + 5) * 60 * 1000; 
    }

    private static long after(int m) {
        return System.currentTimeMillis() - 5 * 60 * 1000; 
    }

    @Test
    public void testNumCars() {
        long result = Queries.numCars(Stream.of(
            new VehicleRecord("12345678", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("12345678", after(10), VehicleSize.S, 100),
            new VehicleRecord("12345678", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("12345678", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("12345678", after(10), VehicleSize.S, 100)
        ));
        assertEquals(2, result);

        // Edge case of no entries
        assertEquals(0, Queries.numCars(Stream.of()));
    }

    @Test
    public void testNumCarsForBusiestSensor() {
        long result = Queries.numCarsForBusiestSensor(Stream.of(
            new VehicleRecord("11111111", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 100),
            new VehicleRecord("11111111", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("11111111", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 100),
            new VehicleRecord("22222222", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("22222222", after(10), VehicleSize.S, 100),
            new VehicleRecord("22222222", before(10), VehicleSize.S, 100), // Old
            new VehicleRecord("22222222", after(10), VehicleSize.S, 100),
            new VehicleRecord("22222222", after(10), VehicleSize.S, 100)
        ));
        assertEquals(3, result);

        // Edge case of no entries
        assertEquals(0, Queries.numCarsForBusiestSensor(Stream.of()));
    }

    @Test
    public void testAvgSpeedForFastestVehicleSize() {
        double result = Queries.avgSpeedForFastestVehicleSize(Stream.of(
            new VehicleRecord("11111111", before(10), VehicleSize.S, 999), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 10),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 20),
            new VehicleRecord("11111111", after(10), VehicleSize.L, 30),
            new VehicleRecord("11111111", after(10), VehicleSize.XL, 10),
            new VehicleRecord("22222222", after(10), VehicleSize.S, 100),
            new VehicleRecord("22222222", after(10), VehicleSize.M, 200),
            new VehicleRecord("22222222", after(10), VehicleSize.L, 300),
            new VehicleRecord("22222222", after(10), VehicleSize.XL, 10)
        ));
        assertEquals(165, result, 0.01);

        // Edge case of no entries
        assertEquals(0, Queries.avgSpeedForFastestVehicleSize(Stream.of()), 0.01);
    }

    @Test
    public void testAvgSpeed() {
        double result = Queries.avgSpeed(Stream.of(
            new VehicleRecord("11111111", before(10), VehicleSize.S, 999), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 10),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 20),
            new VehicleRecord("11111111", after(10), VehicleSize.L, 30),
            new VehicleRecord("11111111", after(10), VehicleSize.XL, 40)
        ));
        assertEquals(25, result, 0.01);

        // Edge case of no entries
        assertEquals(0, Queries.avgSpeed(Stream.of()), 0.01);
    }

    @Test
    public void testNumSmallOrMediumExceeding110KMph() {
        long result = Queries.numSmallOrMediumExceeding110KMph(Stream.of(
            new VehicleRecord("11111111", before(60), VehicleSize.S, 999), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 10), // Slow
            new VehicleRecord("11111111", after(10), VehicleSize.M, 20), // Slow
            new VehicleRecord("11111111", after(10), VehicleSize.L, 30), // L
            new VehicleRecord("11111111", after(10), VehicleSize.S, 115),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 120),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 120)
        ));
        assertEquals(3, result);

        // Edge case of no entries
        assertEquals(0, Queries.numSmallOrMediumExceeding110KMph(Stream.of()));
    }

    @Test
    public void testPercentageSlowTrafficXLPlus() {
        double result = Queries.percentageSlowTrafficXLPlus(Stream.of(
            new VehicleRecord("11111111", before(60), VehicleSize.S, 999), // Old
            new VehicleRecord("11111111", after(10), VehicleSize.S, 100), // Fast
            new VehicleRecord("11111111", after(10), VehicleSize.M, 200), // Fast
            new VehicleRecord("11111111", after(10), VehicleSize.L, 300), // L
            new VehicleRecord("11111111", after(10), VehicleSize.S, 15),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 20),
            new VehicleRecord("11111111", after(10), VehicleSize.M, 20),
            new VehicleRecord("11111111", after(10), VehicleSize.XXL, 20)
        ));
        assertEquals(25, result, 0.01);

        // Edge case of no entries
        assertEquals(0, Queries.percentageSlowTrafficXLPlus(Stream.of()), 0.1);
    }
}