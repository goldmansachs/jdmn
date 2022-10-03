package com.gs.dmn.feel.lib.type.time;

import java.time.LocalDate;
import java.time.OffsetTime;

class BaseDateTimeLibStub extends BaseDateTimeLib {
    @Override
    protected boolean isTime(String literal) {
        return super.isTime(literal);
    }

    @Override
    protected boolean hasTime(String literal) {
        return super.hasTime(literal);
    }

    @Override
    protected boolean hasZoneId(String literal) {
        return super.hasZoneId(literal);
    }

    @Override
    protected boolean hasZoneOffset(String literal) {
        return super.hasZoneOffset(literal);
    }

    @Override
    protected boolean timeHasOffset(String literal) {
        return super.timeHasOffset(literal);
    }

    @Override
    protected String fixDateTimeFormat(String literal) {
        return super.fixDateTimeFormat(literal);
    }

    @Override
    protected LocalDate makeLocalDate(String literal) {
        return super.makeLocalDate(literal);
    }

    @Override
    protected OffsetTime makeOffsetTime(String literal) {
        return super.makeOffsetTime(literal);
    }
}
