package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class StandardJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> makeFEELLib() {
        return new DefaultFEELLib();
    }

    @Override
    protected Class<BigDecimal> getNumberClass() {
        return BigDecimal.class;
    }

    @Override
    protected Class<XMLGregorianCalendar> getDateClass() {
        return XMLGregorianCalendar.class;
    }

    @Override
    protected Class<XMLGregorianCalendar> getTimeClass() {
        return XMLGregorianCalendar.class;
    }

    @Override
    protected Class<XMLGregorianCalendar> getDateTimeClass() {
        return XMLGregorianCalendar.class;
    }

    @Override
    protected Class<Duration> getDurationClass() {
        return Duration.class;
    }
}

