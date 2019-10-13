package com.nikgrozev.trafficcounter.model;

import static org.junit.Assert.*;

import org.junit.Test;
import com.nikgrozev.trafficcounter.model.VehicleRecord;
import com.nikgrozev.trafficcounter.model.VehicleSize;

public class VehicleRecordTest {

    @Test
    public void testEquality() {
        assertEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58));

        assertNotEquals(new VehicleRecord("28673541", 1480529734000L, VehicleSize.M, 58), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58));

        assertNotEquals(new VehicleRecord("18673541", 2480529734000L, VehicleSize.M, 58), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58));

        assertNotEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.XL, 58), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58));

        assertNotEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 158), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58));
    }

    @Test
    public void testHascode() {
        assertEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode(), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode());

        assertNotEquals(new VehicleRecord("28673541", 1480529734000L, VehicleSize.M, 58).hashCode(), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode());

        assertNotEquals(new VehicleRecord("18673541", 2480529734000L, VehicleSize.M, 58).hashCode(), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode());

        assertNotEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.XL, 58).hashCode(), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode());

        assertNotEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 158).hashCode(), 
            new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58).hashCode());
    }

}