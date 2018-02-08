package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tDateTimeComponents"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TDateTimeComponentsImpl implements TDateTimeComponents {
        private java.math.BigDecimal year;
        private java.math.BigDecimal month;
        private java.math.BigDecimal day;
        private java.math.BigDecimal hour;
        private java.math.BigDecimal minute;
        private java.math.BigDecimal second;

    public TDateTimeComponentsImpl() {
    }

    public TDateTimeComponentsImpl(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year) {
        this.setDay(day);
        this.setHour(hour);
        this.setMinute(minute);
        this.setMonth(month);
        this.setSecond(second);
        this.setYear(year);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Day")
    public java.math.BigDecimal getDay() {
        return this.day;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Day")
    public void setDay(java.math.BigDecimal day) {
        this.day = day;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Hour")
    public java.math.BigDecimal getHour() {
        return this.hour;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Hour")
    public void setHour(java.math.BigDecimal hour) {
        this.hour = hour;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Minute")
    public java.math.BigDecimal getMinute() {
        return this.minute;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Minute")
    public void setMinute(java.math.BigDecimal minute) {
        this.minute = minute;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Month")
    public java.math.BigDecimal getMonth() {
        return this.month;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Month")
    public void setMonth(java.math.BigDecimal month) {
        this.month = month;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Second")
    public java.math.BigDecimal getSecond() {
        return this.second;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Second")
    public void setSecond(java.math.BigDecimal second) {
        this.second = second;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Year")
    public java.math.BigDecimal getYear() {
        return this.year;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Year")
    public void setYear(java.math.BigDecimal year) {
        this.year = year;
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
