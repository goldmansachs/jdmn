package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tCompositeDateTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TCompositeDateTimeImpl implements TCompositeDateTime {
        private java.time.LocalDate date;
        private java.time.temporal.TemporalAccessor time;
        private java.time.temporal.TemporalAccessor dateTime;

    public TCompositeDateTimeImpl() {
    }

    public TCompositeDateTimeImpl(java.time.LocalDate date, java.time.temporal.TemporalAccessor dateTime, java.time.temporal.TemporalAccessor time) {
        this.setDate(date);
        this.setDateTime(dateTime);
        this.setTime(time);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Date")
    public java.time.LocalDate getDate() {
        return this.date;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Date")
    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    public java.time.temporal.TemporalAccessor getDateTime() {
        return this.dateTime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DateTime")
    public void setDateTime(java.time.temporal.TemporalAccessor dateTime) {
        this.dateTime = dateTime;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Time")
    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Time")
    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
