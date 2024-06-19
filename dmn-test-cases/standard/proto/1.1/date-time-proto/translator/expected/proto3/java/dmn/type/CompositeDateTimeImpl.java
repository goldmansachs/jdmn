package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "CompositeDateTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompositeDateTimeImpl implements CompositeDateTime {
        private java.time.LocalDate date;
        private java.time.temporal.TemporalAccessor time;
        private java.time.temporal.TemporalAccessor dateTime;
        private java.time.temporal.TemporalAmount yearMonthDuration;
        private java.time.temporal.TemporalAmount dayTimeDuration;

    public CompositeDateTimeImpl() {
    }

    public CompositeDateTimeImpl(java.time.LocalDate date, java.time.temporal.TemporalAccessor dateTime, java.time.temporal.TemporalAmount dayTimeDuration, java.time.temporal.TemporalAccessor time, java.time.temporal.TemporalAmount yearMonthDuration) {
        this.setDate(date);
        this.setDateTime(dateTime);
        this.setDayTimeDuration(dayTimeDuration);
        this.setTime(time);
        this.setYearMonthDuration(yearMonthDuration);
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

    @com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    public java.time.temporal.TemporalAmount getDayTimeDuration() {
        return this.dayTimeDuration;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DayTimeDuration")
    public void setDayTimeDuration(java.time.temporal.TemporalAmount dayTimeDuration) {
        this.dayTimeDuration = dayTimeDuration;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Time")
    public java.time.temporal.TemporalAccessor getTime() {
        return this.time;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Time")
    public void setTime(java.time.temporal.TemporalAccessor time) {
        this.time = time;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    public java.time.temporal.TemporalAmount getYearMonthDuration() {
        return this.yearMonthDuration;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("YearMonthDuration")
    public void setYearMonthDuration(java.time.temporal.TemporalAmount yearMonthDuration) {
        this.yearMonthDuration = yearMonthDuration;
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
