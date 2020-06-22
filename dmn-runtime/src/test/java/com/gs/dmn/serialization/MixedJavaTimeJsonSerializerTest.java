package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class MixedJavaTimeJsonSerializerTest extends AbstractJavaTimeJsonSerializerTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected FEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> makeFEELLib() {
        return new MixedJavaTimeFEELLib();
    }

    @Override
    protected BigDecimal readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, BigDecimal.class);
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
}

