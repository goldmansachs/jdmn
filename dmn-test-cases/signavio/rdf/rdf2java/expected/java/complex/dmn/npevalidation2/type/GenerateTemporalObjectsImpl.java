package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "generateTemporalObjects"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GenerateTemporalObjectsImpl implements GenerateTemporalObjects {
        private javax.xml.datatype.XMLGregorianCalendar date;
        private javax.xml.datatype.XMLGregorianCalendar datetime;

    public GenerateTemporalObjectsImpl() {
    }

    public GenerateTemporalObjectsImpl(javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar datetime) {
        this.setDate(date);
        this.setDatetime(datetime);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("date")
    public javax.xml.datatype.XMLGregorianCalendar getDate() {
        return this.date;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("date")
    public void setDate(javax.xml.datatype.XMLGregorianCalendar date) {
        this.date = date;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("datetime")
    public javax.xml.datatype.XMLGregorianCalendar getDatetime() {
        return this.datetime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("datetime")
    public void setDatetime(javax.xml.datatype.XMLGregorianCalendar datetime) {
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
