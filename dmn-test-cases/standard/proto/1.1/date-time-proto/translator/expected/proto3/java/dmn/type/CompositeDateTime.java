package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "CompositeDateTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.CompositeDateTimeImpl.class)
public interface CompositeDateTime extends com.gs.dmn.runtime.DMNType {
    static CompositeDateTime toCompositeDateTime(Object other) {
        if (other == null) {
            return null;
        } else if (CompositeDateTime.class.isAssignableFrom(other.getClass())) {
            return (CompositeDateTime)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            CompositeDateTimeImpl result_ = new CompositeDateTimeImpl();
            result_.setDate((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("Date"));
            result_.setTime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("Time"));
            result_.setDateTime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("DateTime"));
            result_.setYearMonthDuration((javax.xml.datatype.Duration)((com.gs.dmn.runtime.Context)other).get("YearMonthDuration"));
            result_.setDayTimeDuration((javax.xml.datatype.Duration)((com.gs.dmn.runtime.Context)other).get("DayTimeDuration"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toCompositeDateTime(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else if (other instanceof proto.CompositeDateTime) {
            CompositeDateTimeImpl result_ = new CompositeDateTimeImpl();
            result_.setDate(com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date(((proto.CompositeDateTime) other).getDate()));
            result_.setTime(com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.time(((proto.CompositeDateTime) other).getTime()));
            result_.setDateTime(com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime(((proto.CompositeDateTime) other).getDateTime()));
            result_.setYearMonthDuration(com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.duration(((proto.CompositeDateTime) other).getYearMonthDuration()));
            result_.setDayTimeDuration(com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.duration(((proto.CompositeDateTime) other).getDayTimeDuration()));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), CompositeDateTime.class.getSimpleName()));
        }
    }

    static proto.CompositeDateTime toProto(CompositeDateTime other) {
        proto.CompositeDateTime.Builder result_ = proto.CompositeDateTime.newBuilder();
        if (other != null) {
            String dateProto_ = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.string(((CompositeDateTime) other).getDate());
            result_.setDate(dateProto_);
            String timeProto_ = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.string(((CompositeDateTime) other).getTime());
            result_.setTime(timeProto_);
            String dateTimeProto_ = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.string(((CompositeDateTime) other).getDateTime());
            result_.setDateTime(dateTimeProto_);
            String yearMonthDurationProto_ = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.string(((CompositeDateTime) other).getYearMonthDuration());
            result_.setYearMonthDuration(yearMonthDurationProto_);
            String dayTimeDurationProto_ = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.string(((CompositeDateTime) other).getDayTimeDuration());
            result_.setDayTimeDuration(dayTimeDurationProto_);
        }
        return result_.build();
    }

    static List<proto.CompositeDateTime> toProto(List<CompositeDateTime> other) {
        if (other == null) {
            return null;
        } else {
            return other.stream().map(o -> toProto(o)).collect(java.util.stream.Collectors.toList());
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Date")
    javax.xml.datatype.XMLGregorianCalendar getDate();

    @com.fasterxml.jackson.annotation.JsonGetter("Time")
    javax.xml.datatype.XMLGregorianCalendar getTime();

    @com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    javax.xml.datatype.XMLGregorianCalendar getDateTime();

    @com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    javax.xml.datatype.Duration getYearMonthDuration();

    @com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    javax.xml.datatype.Duration getDayTimeDuration();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("date", getDate());
        context.put("time", getTime());
        context.put("dateTime", getDateTime());
        context.put("yearMonthDuration", getYearMonthDuration());
        context.put("dayTimeDuration", getDayTimeDuration());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeDateTime other = (CompositeDateTime) o;
        if (this.getDate() != null ? !this.getDate().equals(other.getDate()) : other.getDate() != null) return false;
        if (this.getDateTime() != null ? !this.getDateTime().equals(other.getDateTime()) : other.getDateTime() != null) return false;
        if (this.getDayTimeDuration() != null ? !this.getDayTimeDuration().equals(other.getDayTimeDuration()) : other.getDayTimeDuration() != null) return false;
        if (this.getTime() != null ? !this.getTime().equals(other.getTime()) : other.getTime() != null) return false;
        if (this.getYearMonthDuration() != null ? !this.getYearMonthDuration().equals(other.getYearMonthDuration()) : other.getYearMonthDuration() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDate() != null ? this.getDate().hashCode() : 0);
        result = 31 * result + (this.getDateTime() != null ? this.getDateTime().hashCode() : 0);
        result = 31 * result + (this.getDayTimeDuration() != null ? this.getDayTimeDuration().hashCode() : 0);
        result = 31 * result + (this.getTime() != null ? this.getTime().hashCode() : 0);
        result = 31 * result + (this.getYearMonthDuration() != null ? this.getYearMonthDuration().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Date=" + getDate());
        result_.append(", DateTime=" + getDateTime());
        result_.append(", DayTimeDuration=" + getDayTimeDuration());
        result_.append(", Time=" + getTime());
        result_.append(", YearMonthDuration=" + getYearMonthDuration());
        result_.append("}");
        return result_.toString();
    }
}
