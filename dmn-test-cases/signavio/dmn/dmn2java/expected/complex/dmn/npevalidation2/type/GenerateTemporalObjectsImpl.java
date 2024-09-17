package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "generateTemporalObjects"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GenerateTemporalObjectsImpl implements GenerateTemporalObjects {
        private java.time.LocalDate date;
        private java.time.temporal.TemporalAccessor datetime;

    public GenerateTemporalObjectsImpl() {
    }

    public GenerateTemporalObjectsImpl(java.time.LocalDate date, java.time.temporal.TemporalAccessor datetime) {
        this.setDate(date);
        this.setDatetime(datetime);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("date")
    public java.time.LocalDate getDate() {
        return this.date;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("date")
    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("datetime")
    public java.time.temporal.TemporalAccessor getDatetime() {
        return this.datetime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("datetime")
    public void setDatetime(java.time.temporal.TemporalAccessor datetime) {
        this.datetime = datetime;
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
