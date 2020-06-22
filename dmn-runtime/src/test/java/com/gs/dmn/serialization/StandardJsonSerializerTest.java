package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class StandardJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> makeFEELLib() {
        return new DefaultFEELLib();
    }

    @Override
    protected BigDecimal readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, BigDecimal.class);
    }

    @Override
    protected XMLGregorianCalendar readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected Duration readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Duration.class);
    }
}

