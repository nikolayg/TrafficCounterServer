package com.nikgrozev.trafficcounter.db;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nikgrozev.trafficcounter.model.VehicleRecord;
import com.nikgrozev.trafficcounter.model.VehicleSize;

/**
 * Queries on the common data store.
 */
public class Queries {

    // Common functions for queries ...
    private static final Predicate<VehicleRecord> LAST_10_MINS = v -> {
        long now = System.currentTimeMillis();
        return v.getTimeStamp() >= now - (10 * 60 * 1000);
    };
    private static final Predicate<VehicleRecord> LAST_HOUR = v -> {
        long now = System.currentTimeMillis();
        return v.getTimeStamp() >= now - (60 * 60 * 1000);
    };

    private static final Function<VehicleRecord, String> ID = v -> v.getCounterId();
    private static final Function<VehicleRecord, VehicleSize> SIZE = v -> v.getSize();
    private static final ToIntFunction<VehicleRecord> SPEED = v -> v.getSpeed();
    private static final Function<List<VehicleRecord>, Double> AVG_SPEED = 
        l -> l.stream().mapToInt(v -> v.getSpeed()).average().orElse(0);

    public static long numCars(Stream<VehicleRecord> recs) {
        return recs.filter(LAST_10_MINS).count();
    }

    public static int numCarsForBusiestSensor(Stream<VehicleRecord> recs) {
        // Group them by ids
        Map<String, List<VehicleRecord>> byIds = recs.filter(LAST_10_MINS).collect(Collectors.groupingBy(ID));

        // Number of cars comparator
        Comparator<Map.Entry<String, List<VehicleRecord>>> cmp = 
            Comparator.comparing(kv -> kv.getValue().size());

        // Get the max and then the number
        return byIds.entrySet().
            stream().
            max(cmp).
            map(kv -> kv.getValue().size()).
            orElse(0);
    }

    public static double avgSpeedForFastestVehicleSize(Stream<VehicleRecord> recs) {
        // Group them by size
        Map<VehicleSize, List<VehicleRecord>> bySize = recs.filter(LAST_10_MINS).collect(Collectors.groupingBy(SIZE));

        // Avg Speed comparator
        Comparator<Map.Entry<VehicleSize, List<VehicleRecord>>> cmp = 
            Comparator.comparing(kv -> AVG_SPEED.apply(kv.getValue()));

        // Get the max and then the number
        return bySize.entrySet().
            stream().
            max(cmp).
            map(kv -> AVG_SPEED.apply(kv.getValue())).
            orElse(0d);
    }

    public static double avgSpeed(Stream<VehicleRecord> recs) {
        return recs.filter(LAST_10_MINS).
            mapToInt(SPEED).
            average().
            orElse(0);
    }

    public static long numSmallOrMediumExceeding110KMph(Stream<VehicleRecord> recs) {
        Set<VehicleSize> sizes = EnumSet.of(VehicleSize.S, VehicleSize.M);
        return recs.
            filter(LAST_HOUR).
            filter(v -> v.getSpeed() > 110 && sizes.contains(v.getSize())).
            count();
    }

    public static double percentageSlowTrafficXLPlus(Stream<VehicleRecord> recs) {
        Set<VehicleSize> sizes = EnumSet.of(VehicleSize.XL, VehicleSize.XXL);
        List<VehicleRecord> slow = recs.filter(LAST_HOUR).filter(v -> v.getSpeed() < 30).collect(Collectors.toList());
        long overall = slow.size();
        long matchingSizes = slow.stream().filter(v -> sizes.contains(v.getSize())).count();

        // TODO: should we return 0% or 100% if there're not slow vehicles?
        return overall == 0 ? 0 : 100 * ((double) matchingSizes / overall);
    }
}