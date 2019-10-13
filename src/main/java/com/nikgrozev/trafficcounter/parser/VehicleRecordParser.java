package com.nikgrozev.trafficcounter.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.nikgrozev.trafficcounter.model.VehicleRecord;
import com.nikgrozev.trafficcounter.model.VehicleSize;

/**
 * Parses the counterId_yyyy-mm-ddThh:mm:ss_vehicleSize_vehicleSpeed format;
 */
public class VehicleRecordParser {
    private static final String COUNTER_PATTERN = "\\d{8}";
    private static final String TIMESTAMP_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    private static final String SIZE_PATTERN = "(S|M|L|XL|XXL)";
    private static final String SPEED_PATTERN = "\\d{1,3}";

    private static final String VALIDATION_PATTERN = String.join("_",
            new String[] { COUNTER_PATTERN, TIMESTAMP_PATTERN, SIZE_PATTERN, SPEED_PATTERN });
            
    private static DateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 

    private static long parseTimestamp(String timestamp) {
        try {
            return DATE_FMT.parse(timestamp).getTime();
        } catch (ParseException e) {
            throw new InvalidFormatException("Invalid timestamp " + timestamp);
        }
    }

    private static VehicleRecord parseVehicleRecord(String record) {
        if (record == null || !record.matches(VALIDATION_PATTERN)) {
            throw new InvalidFormatException(record + " does not match expected pattern");
        }
        //Get the separate parts of the record
        String[] parts = record.split("_");

        String counterId = parts[0];
        long timeStamp = parseTimestamp(parts[1]);
        VehicleSize vehicleSize = VehicleSize.valueOf(parts[2]);
        int vehicleSpeed = Integer.parseInt(parts[3]);

        return new VehicleRecord(counterId, timeStamp, vehicleSize, vehicleSpeed);
    }

    public List<VehicleRecord> parseVehicleRecords(String records) {
        String[] recordsTxt = records.split("\\s*,\\s*");
        return Arrays.stream(recordsTxt).map(VehicleRecordParser::parseVehicleRecord).collect(Collectors.toList());
    }
}