package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tDateVariants"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TDateVariants extends com.gs.dmn.runtime.DMNType {
    static TDateVariants toTDateVariants(Object other) {
        if (other == null) {
            return null;
        } else if (TDateVariants.class.isAssignableFrom(other.getClass())) {
            return (TDateVariants)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TDateVariantsImpl result_ = new TDateVariantsImpl();
            result_.setFromString((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("fromString"));
            result_.setFromDateTime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("fromDateTime"));
            result_.setFromYearMonthDay((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("fromYearMonthDay"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTDateVariants(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TDateVariants.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fromString")
    javax.xml.datatype.XMLGregorianCalendar getFromString();

    @com.fasterxml.jackson.annotation.JsonGetter("fromDateTime")
    javax.xml.datatype.XMLGregorianCalendar getFromDateTime();

    @com.fasterxml.jackson.annotation.JsonGetter("fromYearMonthDay")
    javax.xml.datatype.XMLGregorianCalendar getFromYearMonthDay();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("fromString", getFromString());
        context.put("fromDateTime", getFromDateTime());
        context.put("fromYearMonthDay", getFromYearMonthDay());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDateVariants other = (TDateVariants) o;
        if (this.getFromDateTime() != null ? !this.getFromDateTime().equals(other.getFromDateTime()) : other.getFromDateTime() != null) return false;
        if (this.getFromString() != null ? !this.getFromString().equals(other.getFromString()) : other.getFromString() != null) return false;
        if (this.getFromYearMonthDay() != null ? !this.getFromYearMonthDay().equals(other.getFromYearMonthDay()) : other.getFromYearMonthDay() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getFromDateTime() != null ? this.getFromDateTime().hashCode() : 0);
        result = 31 * result + (this.getFromString() != null ? this.getFromString().hashCode() : 0);
        result = 31 * result + (this.getFromYearMonthDay() != null ? this.getFromYearMonthDay().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("fromDateTime=" + getFromDateTime());
        result_.append(", fromString=" + getFromString());
        result_.append(", fromYearMonthDay=" + getFromYearMonthDay());
        result_.append("}");
        return result_.toString();
    }
}
