package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tDateVariants"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TDateVariantsImpl implements TDateVariants {
        private javax.xml.datatype.XMLGregorianCalendar fromString;
        private javax.xml.datatype.XMLGregorianCalendar fromDateTime;
        private javax.xml.datatype.XMLGregorianCalendar fromYearMonthDay;

    public TDateVariantsImpl() {
    }

    public TDateVariantsImpl(javax.xml.datatype.XMLGregorianCalendar fromDateTime, javax.xml.datatype.XMLGregorianCalendar fromString, javax.xml.datatype.XMLGregorianCalendar fromYearMonthDay) {
        this.setFromDateTime(fromDateTime);
        this.setFromString(fromString);
        this.setFromYearMonthDay(fromYearMonthDay);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fromDateTime")
    public javax.xml.datatype.XMLGregorianCalendar getFromDateTime() {
        return this.fromDateTime;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("fromDateTime")
    public void setFromDateTime(javax.xml.datatype.XMLGregorianCalendar fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fromString")
    public javax.xml.datatype.XMLGregorianCalendar getFromString() {
        return this.fromString;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("fromString")
    public void setFromString(javax.xml.datatype.XMLGregorianCalendar fromString) {
        this.fromString = fromString;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fromYearMonthDay")
    public javax.xml.datatype.XMLGregorianCalendar getFromYearMonthDay() {
        return this.fromYearMonthDay;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("fromYearMonthDay")
    public void setFromYearMonthDay(javax.xml.datatype.XMLGregorianCalendar fromYearMonthDay) {
        this.fromYearMonthDay = fromYearMonthDay;
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
