package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tCompositeDateTime"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TCompositeDateTimeImpl.class)
public interface TCompositeDateTime extends com.gs.dmn.runtime.DMNType {
    static TCompositeDateTime toTCompositeDateTime(Object other) {
        if (other == null) {
            return null;
        } else if (TCompositeDateTime.class.isAssignableFrom(other.getClass())) {
            return (TCompositeDateTime)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TCompositeDateTimeImpl result_ = new TCompositeDateTimeImpl();
            result_.setDate((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("Date", "Date"));
            result_.setTime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("Time", "Time"));
            result_.setDateTime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("DateTime", "DateTime"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTCompositeDateTime(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else if (other instanceof proto.TCompositeDateTime) {
            TCompositeDateTimeImpl result_ = new TCompositeDateTimeImpl();
            result_.setDate(com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(((proto.TCompositeDateTime) other).getDate()));
            result_.setTime(com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(((proto.TCompositeDateTime) other).getTime()));
            result_.setDateTime(com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(((proto.TCompositeDateTime) other).getDateTime()));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TCompositeDateTime.class.getSimpleName()));
        }
    }

    static proto.TCompositeDateTime toProto(TCompositeDateTime other) {
        proto.TCompositeDateTime.Builder result_ = proto.TCompositeDateTime.newBuilder();
        if (other != null) {
            String dateProto_ = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getDate());
            result_.setDate(dateProto_);
            String timeProto_ = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getTime());
            result_.setTime(timeProto_);
            String dateTimeProto_ = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getDateTime());
            result_.setDateTime(dateTimeProto_);
        }
        return result_.build();
    }

    static List<proto.TCompositeDateTime> toProto(List<TCompositeDateTime> other) {
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

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("date", getDate());
        context.put("time", getTime());
        context.put("dateTime", getDateTime());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCompositeDateTime other = (TCompositeDateTime) o;
        if (this.getDate() != null ? !this.getDate().equals(other.getDate()) : other.getDate() != null) return false;
        if (this.getDateTime() != null ? !this.getDateTime().equals(other.getDateTime()) : other.getDateTime() != null) return false;
        if (this.getTime() != null ? !this.getTime().equals(other.getTime()) : other.getTime() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDate() != null ? this.getDate().hashCode() : 0);
        result = 31 * result + (this.getDateTime() != null ? this.getDateTime().hashCode() : 0);
        result = 31 * result + (this.getTime() != null ? this.getTime().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Date=" + getDate());
        result_.append(", DateTime=" + getDateTime());
        result_.append(", Time=" + getTime());
        result_.append("}");
        return result_.toString();
    }
}
