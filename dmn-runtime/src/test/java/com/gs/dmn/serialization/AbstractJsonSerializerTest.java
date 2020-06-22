package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractJsonSerializerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib = makeFEELLib();

    @Test
    public void testNumber() throws Exception {
        List<Pair<String, String>> pairs = Arrays.asList(
                new Pair<>("123", "123"),
                new Pair<>("-123", "-123"),
                new Pair<>("123.45", "123.45"),
                new Pair<>("-123.45", "-123.45")
        );

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = pair.getRight();

            // Serialization
            NUMBER obj1 = lib.number(literal);
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1));

            // Deserialization
            NUMBER obj2 = JsonSerializer.OBJECT_MAPPER.readValue(literal, getNumberClass());
            assertEquals(obj1, obj2);

            // Round-trip serialization
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    @Test
    public void testDate() throws Exception {
        List<Pair<String, String>> pairs = Arrays.asList(
                new Pair<>("2019-03-12", "2019-03-12"),
                new Pair<>("9999-12-31", "9999-12-31")
        );

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = String.format("\"%s\"", pair.getRight());

            // Serialization
            DATE obj1 = lib.date(literal);
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1));

            // Deserialization
            DATE obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, getDateClass());
            assertEquals(obj1, obj2);

            // Round-trip serialization
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    @Test
    public void testTime() throws Exception {
        List<Pair<String, String>> pairs = Arrays.asList(
                new Pair<>("04:20:20", "04:20:20"),
                new Pair<>("04:20:20Z", "04:20:20Z"),
                new Pair<>("04:20:20.004", "04:20:20.004"),
                new Pair<>("04:20:20.004Z", "04:20:20.004Z"),
                new Pair<>("04:20:20.00421", "04:20:20.00421"),
                new Pair<>("04:20:20.00421Z", "04:20:20.00421Z"),
                new Pair<>("04:20:20.00421+01:00", "04:20:20.00421+01:00"),
                new Pair<>("04:20:20.004@UTC", "04:20:20.004Z"),
                new Pair<>("04:20:20.00421@Europe/Paris", "04:20:20.00421+01:00")
        );

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = String.format("\"%s\"", pair.getRight());

            // Serialization
            TIME obj1 = lib.time(literal);
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1));

            // Deserialization
            TIME obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, getTimeClass());
            assertEquals(obj1, obj2);

            // Round-trip serialization
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    @Test
    public void testDateAndTime() throws Exception {
        List<Pair<String, String>> pairs = Arrays.asList(
                new Pair<>("2019-03-11T04:20:20", "2019-03-11T04:20:20"),
                new Pair<>("2019-03-11T04:20:20Z", "2019-03-11T04:20:20Z"),
                new Pair<>("2019-03-11T04:20:20.004", "2019-03-11T04:20:20.004"),
                new Pair<>("2019-03-11T04:20:20.004Z", "2019-03-11T04:20:20.004Z"),
                new Pair<>("2019-03-11T04:20:20.00421", "2019-03-11T04:20:20.00421"),
                new Pair<>("2019-03-11T04:20:20.00421Z", "2019-03-11T04:20:20.00421Z"),
                new Pair<>("2019-03-11T04:20:20.00421+01:00", "2019-03-11T04:20:20.00421+01:00"),
                new Pair<>("2019-03-11T04:20:20.004@UTC", "2019-03-11T04:20:20.004Z"),
                new Pair<>("2019-03-11T04:20:20.00421@Europe/Paris", "2019-03-11T04:20:20.00421+01:00"),

                new Pair<>("9999-03-11T04:20:20", "9999-03-11T04:20:20")
        );

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = String.format("\"%s\"", pair.getRight());

            // Serialization
            DATE_TIME obj1 = lib.dateAndTime(literal);
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1));

            // Deserialization
            DATE_TIME obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, getDateTimeClass());
            assertEquals(obj1, obj2);

            // Round-trip serialization
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    @Test
    public void testDuration() throws Exception {
        List<Pair<String, String>> pairs = Arrays.asList(
                new Pair<>("P1Y2M", "P1Y2M"),
                new Pair<>("P1DT2H3M", "P1DT2H3M"),
                new Pair<>("P1Y1M3DT4H5M", "P1Y1M3DT4H5M")
        );

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = String.format("\"%s\"", pair.getRight());

            // Serialization
            DURATION obj1 = lib.duration(literal);
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1));

            // Deserialization
            DURATION obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, getDurationClass());
            assertEquals(obj1, obj2);

            // Round-trip serialization
            checkSerialization(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    private void checkSerialization(String expectedSerialization, String actualSerialization) {
        assertEquals(expectedSerialization, actualSerialization);
    }

    protected abstract FEELLib<NUMBER,DATE,TIME,DATE_TIME,DURATION> makeFEELLib();
    protected abstract Class<NUMBER> getNumberClass();
    protected abstract Class<DATE> getDateClass();
    protected abstract Class<TIME> getTimeClass();
    protected abstract Class<DATE_TIME> getDateTimeClass();
    protected abstract Class<DURATION> getDurationClass();

}