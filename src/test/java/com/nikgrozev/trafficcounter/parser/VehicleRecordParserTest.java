package com.nikgrozev.trafficcounter.parser;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;

import com.nikgrozev.trafficcounter.model.VehicleRecord;
import com.nikgrozev.trafficcounter.model.VehicleSize;

public class VehicleRecordParserTest {

    private static VehicleRecordParser parser = new VehicleRecordParser();

    @Test
    public void parseSingleGoodInput() {
        List<VehicleRecord> records = parser.parseVehicleRecords("18673541_2016-12-01T05:15:34_M_58");
        assertEquals(1, records.size());
        assertEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58), records.get(0));

        records = parser.parseVehicleRecords("18673541_2016-12-01T05:25:34_XXL_158");
        assertEquals(1, records.size());
        assertEquals(new VehicleRecord("18673541", 1480530334000L, VehicleSize.XXL, 158), records.get(0));
    }

    @Test
    public void parseSingleBadInput() {
        assertThrows(InvalidFormatException.class, () -> parser.parseVehicleRecords("gibberish"));

        // 7 digit id - it must be 8
        assertThrows(InvalidFormatException.class, () -> parser.parseVehicleRecords("1867354_2016-12-01T05:15:34_M_58"));
        
        // Invalid vehicle size T
        assertThrows(InvalidFormatException.class, () -> parser.parseVehicleRecords("18673541_2016-12-01T05:15:34_T_58"));

        // Invalid vehicle speed
        assertThrows(InvalidFormatException.class, () -> parser.parseVehicleRecords("18673541_2016-12-01T05:15:34_T_1000"));
    }

    @Test
    public void parseMultipleGoodInputs() {
        List<VehicleRecord> records = parser.parseVehicleRecords("18673541_2016-12-01T05:15:34_M_58,18673541_2016-12-01T05:25:34_XXL_158");
        assertEquals(2, records.size());
        assertEquals(new VehicleRecord("18673541", 1480529734000L, VehicleSize.M, 58), records.get(0));
        assertEquals(new VehicleRecord("18673541", 1480530334000L, VehicleSize.XXL, 158), records.get(1));
    }

    @Test
    public void parseMultipleBadInputs() {
        // 1st record is invalid
        assertThrows(InvalidFormatException.class, () -> parser.parseVehicleRecords("1867354_2016-12-01T05:15:34_M_58,11867354_2016-12-01T05:15:34_M_58"));
    }
}