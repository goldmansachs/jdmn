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
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("Date") || ((com.gs.dmn.runtime.Context)other).keySet().contains("Date")) {
                result_.setDate((java.time.LocalDate)((com.gs.dmn.runtime.Context)other).get("Date", "Date"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("Time") || ((com.gs.dmn.runtime.Context)other).keySet().contains("Time")) {
                result_.setTime((java.time.temporal.TemporalAccessor)((com.gs.dmn.runtime.Context)other).get("Time", "Time"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("DateTime") || ((com.gs.dmn.runtime.Context)other).keySet().contains("DateTime")) {
                result_.setDateTime((java.time.temporal.TemporalAccessor)((com.gs.dmn.runtime.Context)other).get("DateTime", "DateTime"));
            } else {
                return  null;
            }
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTCompositeDateTime(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else if (other instanceof proto.TCompositeDateTime) {
            TCompositeDateTimeImpl result_ = new TCompositeDateTimeImpl();
            result_.setDate(com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(((proto.TCompositeDateTime) other).getDate()));
            result_.setTime(com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(((proto.TCompositeDateTime) other).getTime()));
            result_.setDateTime(com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(((proto.TCompositeDateTime) other).getDateTime()));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TCompositeDateTime.class.getSimpleName()));
        }
    }

    static proto.TCompositeDateTime toProto(TCompositeDateTime other) {
        proto.TCompositeDateTime.Builder result_ = proto.TCompositeDateTime.newBuilder();
        if (other != null) {
            String dateProto_ = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getDate());
            result_.setDate(dateProto_);
            String timeProto_ = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getTime());
            result_.setTime(timeProto_);
            String dateTimeProto_ = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.string(((TCompositeDateTime) other).getDateTime());
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
    java.time.LocalDate getDate();

    @com.fasterxml.jackson.annotation.JsonGetter("Time")
    java.time.temporal.TemporalAccessor getTime();

    @com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    java.time.temporal.TemporalAccessor getDateTime();

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
