package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tDateTimeComponents"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TDateTimeComponents extends com.gs.dmn.runtime.DMNType {
    static TDateTimeComponents toTDateTimeComponents(Object other) {
        if (other == null) {
            return null;
        } else if (TDateTimeComponents.class.isAssignableFrom(other.getClass())) {
            return (TDateTimeComponents)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TDateTimeComponentsImpl result_ = new TDateTimeComponentsImpl();
            result_.setYear((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Year"));
            result_.setMonth((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Month"));
            result_.setDay((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Day"));
            result_.setHour((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Hour"));
            result_.setMinute((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Minute"));
            result_.setSecond((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("Second"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTDateTimeComponents(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TDateTimeComponents.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Year")
    java.math.BigDecimal getYear();

    @com.fasterxml.jackson.annotation.JsonGetter("Month")
    java.math.BigDecimal getMonth();

    @com.fasterxml.jackson.annotation.JsonGetter("Day")
    java.math.BigDecimal getDay();

    @com.fasterxml.jackson.annotation.JsonGetter("Hour")
    java.math.BigDecimal getHour();

    @com.fasterxml.jackson.annotation.JsonGetter("Minute")
    java.math.BigDecimal getMinute();

    @com.fasterxml.jackson.annotation.JsonGetter("Second")
    java.math.BigDecimal getSecond();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("year", getYear());
        context.put("month", getMonth());
        context.put("day", getDay());
        context.put("hour", getHour());
        context.put("minute", getMinute());
        context.put("second", getSecond());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDateTimeComponents other = (TDateTimeComponents) o;
        if (this.getDay() != null ? !this.getDay().equals(other.getDay()) : other.getDay() != null) return false;
        if (this.getHour() != null ? !this.getHour().equals(other.getHour()) : other.getHour() != null) return false;
        if (this.getMinute() != null ? !this.getMinute().equals(other.getMinute()) : other.getMinute() != null) return false;
        if (this.getMonth() != null ? !this.getMonth().equals(other.getMonth()) : other.getMonth() != null) return false;
        if (this.getSecond() != null ? !this.getSecond().equals(other.getSecond()) : other.getSecond() != null) return false;
        if (this.getYear() != null ? !this.getYear().equals(other.getYear()) : other.getYear() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDay() != null ? this.getDay().hashCode() : 0);
        result = 31 * result + (this.getHour() != null ? this.getHour().hashCode() : 0);
        result = 31 * result + (this.getMinute() != null ? this.getMinute().hashCode() : 0);
        result = 31 * result + (this.getMonth() != null ? this.getMonth().hashCode() : 0);
        result = 31 * result + (this.getSecond() != null ? this.getSecond().hashCode() : 0);
        result = 31 * result + (this.getYear() != null ? this.getYear().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Day=" + getDay());
        result_.append(", Hour=" + getHour());
        result_.append(", Minute=" + getMinute());
        result_.append(", Month=" + getMonth());
        result_.append(", Second=" + getSecond());
        result_.append(", Year=" + getYear());
        result_.append("}");
        return result_.toString();
    }
}
