package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JsonSerializerTest {
    private final DefaultFEELLib lib = new DefaultFEELLib();

    @Test
    public void testDate() throws Exception {
        String[] literals = new String[]{
                "2019-03-12"
        };
        for (String literal : literals) {
            assertEquals(literal, String.format("\"%s\"", literal), JsonSerializer.OBJECT_MAPPER.writeValueAsString(lib.date(literal)));
        }
    }

    @Test
    public void testTime() throws Exception {
        List<String[]> tuples = Arrays.asList(
                new String[] {"04:20:20", "04:20:20"},
                new String[] {"04:20:20Z", "04:20:20Z"},
                new String[] {"04:20:20.004", "04:20:20.004"},
                new String[] {"04:20:20.004Z", "04:20:20.004Z"},
                new String[] {"04:20:20.00421", "04:20:20.00421"},
                new String[] {"04:20:20.00421Z", "04:20:20.00421Z"},
                new String[] {"04:20:20.00421+01:00", "04:20:20.00421+01:00"},
                new String[] {"04:20:20.004@UTC", "04:20:20.004Z"},
                new String[] {"04:20:20.00421@Europe/Paris", "04:20:20.00421+01:00"}
        );
        for (String[] pair : tuples) {
            // Serialization
            String literal = pair[0];
            XMLGregorianCalendar obj1 = lib.time(literal);
            String serialization = JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1);
            String expectedSerialization = pair[1];
            assertEquals(String.format("\"%s\"", expectedSerialization), serialization);

            // Deserialization
            XMLGregorianCalendar obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, XMLGregorianCalendar.class);
            assertEquals(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }

    @Test
    public void testDateAndTime() throws Exception {
        List<String[]> tuples = Arrays.asList(
                new String[] {"2019-03-11T04:20:20", "2019-03-11T04:20:20"},
                new String[] {"2019-03-11T04:20:20Z", "2019-03-11T04:20:20Z"},
                new String[] {"2019-03-11T04:20:20.004", "2019-03-11T04:20:20.004"},
                new String[] {"2019-03-11T04:20:20.004Z", "2019-03-11T04:20:20.004Z"},
                new String[] {"2019-03-11T04:20:20.00421", "2019-03-11T04:20:20.00421"},
                new String[] {"2019-03-11T04:20:20.00421Z", "2019-03-11T04:20:20.00421Z"},
                new String[] {"2019-03-11T04:20:20.00421+01:00", "2019-03-11T04:20:20.00421+01:00"},
                new String[] {"2019-03-11T04:20:20.004@UTC", "2019-03-11T04:20:20.004Z"},
                new String[] {"2019-03-11T04:20:20.00421@Europe/Paris", "2019-03-11T04:20:20.00421+01:00"}
        );
        for (String[] pair : tuples) {
            // Serialization
            String literal = pair[0];
            XMLGregorianCalendar obj1 = lib.dateAndTime(literal);
            String serialization = JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj1);
            String expectedSerialization = pair[1];
            assertEquals(String.format("\"%s\"", expectedSerialization), serialization);

            // Deserialization
            XMLGregorianCalendar obj2 = JsonSerializer.OBJECT_MAPPER.readValue(serialization, XMLGregorianCalendar.class);
            assertEquals(serialization, JsonSerializer.OBJECT_MAPPER.writeValueAsString(obj2));
        }
    }
}