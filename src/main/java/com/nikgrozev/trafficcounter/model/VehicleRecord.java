package com.nikgrozev.trafficcounter.model;

import static org.assertj.core.api.Assertions.*;

/**
 * Immutable class representing an observation about a passing vehicle.
 */
public class VehicleRecord {
    private final String counterId;
    private final long timeStamp;
    private final VehicleSize vehicleSize;
    private final int vehicleSpeed;

    public VehicleRecord(String counterId, long timeStamp, VehicleSize vehicleSize, int vehicleSpeed) {
        assertThat(counterId).isNotNull();
        assertThat(counterId).matches("\\d{8}");
        assertThat(vehicleSize).isNotNull();
        assertThat(vehicleSpeed).isBetween(1, 999);

        this.counterId = counterId;
        this.timeStamp = timeStamp;
        this.vehicleSize = vehicleSize;
        this.vehicleSpeed = vehicleSpeed;
    }

    public String getCounterId() {
        return counterId;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    // Redifine equals, hashCode, and toString - good for testing
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VehicleRecord)) {
            return false;
        }
        VehicleRecord other = (VehicleRecord) o;
        return this.counterId.equals(other.counterId) && 
            this.timeStamp == other.timeStamp && 
            this.vehicleSize.equals(other.vehicleSize) && 
            this.vehicleSpeed == other.vehicleSpeed;
    }

    @Override
    public int hashCode() {
        return counterId.hashCode() ^ 
            Long.valueOf(timeStamp).hashCode() ^ 
            vehicleSize.hashCode() ^ 
            vehicleSpeed; 
    }

    @Override
    public String toString() {
        return String.format("counterId=%s, timeStamp=%d, vehicleSize=%s, vehicleSpeed=%d",
            counterId,
            timeStamp,
            vehicleSize,
            vehicleSpeed
        );
    }
}