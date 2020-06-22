package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.DoubleMixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Pair;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class DoubleMixedJavaTimeJsonSerializerTest extends AbstractJavaTimeJsonSerializerTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected FEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> makeFEELLib() {
        return new DoubleMixedJavaTimeFEELLib();
    }

    @Override
    protected Double readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Double.class);
    }

    @Override
    protected LocalDate readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, LocalDate.class);
    }

    @Override
    protected OffsetTime readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, OffsetTime.class);
    }

    @Override
    protected ZonedDateTime readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, ZonedDateTime.class);
    }

    @Override
    protected Duration readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Duration.class);
    }

    @Override
    protected List<Pair<String, String>> getNumberTestData() {
        return Arrays.asList(
                new Pair<>("123", "123.0"),
                new Pair<>("-123", "-123.0"),
                new Pair<>("123.45", "123.45"),
                new Pair<>("-123.45", "-123.45")
        );
    }
}

