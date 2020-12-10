package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tCompositeDateTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TCompositeDateTimeImpl implements TCompositeDateTime {
        private javax.xml.datatype.XMLGregorianCalendar date;
        private javax.xml.datatype.XMLGregorianCalendar time;
        private javax.xml.datatype.XMLGregorianCalendar dateTime;

    public TCompositeDateTimeImpl() {
    }

    public TCompositeDateTimeImpl(javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, javax.xml.datatype.XMLGregorianCalendar time) {
        this.setDate(date);
        this.setDateTime(dateTime);
        this.setTime(time);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Date")
    public javax.xml.datatype.XMLGregorianCalendar getDate() {
        return this.date;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Date")
    public void setDate(javax.xml.datatype.XMLGregorianCalendar date) {
        this.date = date;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    public javax.xml.datatype.XMLGregorianCalendar getDateTime() {
        return this.dateTime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DateTime")
    public void setDateTime(javax.xml.datatype.XMLGregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Time")
    public javax.xml.datatype.XMLGregorianCalendar getTime() {
        return this.time;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Time")
    public void setTime(javax.xml.datatype.XMLGregorianCalendar time) {
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
