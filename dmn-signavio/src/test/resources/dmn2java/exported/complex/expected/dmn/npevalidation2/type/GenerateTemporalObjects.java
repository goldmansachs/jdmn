package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "generateTemporalObjects"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.GenerateTemporalObjectsImpl.class)
public interface GenerateTemporalObjects extends com.gs.dmn.runtime.DMNType {
    static GenerateTemporalObjects toGenerateTemporalObjects(Object other) {
        if (other == null) {
            return null;
        } else if (GenerateTemporalObjects.class.isAssignableFrom(other.getClass())) {
            return (GenerateTemporalObjects)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            GenerateTemporalObjectsImpl result_ = new GenerateTemporalObjectsImpl();
            result_.setDate((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("date", "date"));
            result_.setDatetime((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("datetime", "datetime"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toGenerateTemporalObjects(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), GenerateTemporalObjects.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("date")
    javax.xml.datatype.XMLGregorianCalendar getDate();

    @com.fasterxml.jackson.annotation.JsonGetter("datetime")
    javax.xml.datatype.XMLGregorianCalendar getDatetime();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("date", getDate());
        context.put("datetime", getDatetime());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenerateTemporalObjects other = (GenerateTemporalObjects) o;
        if (this.getDate() != null ? !this.getDate().equals(other.getDate()) : other.getDate() != null) return false;
        if (this.getDatetime() != null ? !this.getDatetime().equals(other.getDatetime()) : other.getDatetime() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDate() != null ? this.getDate().hashCode() : 0);
        result = 31 * result + (this.getDatetime() != null ? this.getDatetime().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("date=" + getDate());
        result_.append(", datetime=" + getDatetime());
        result_.append("}");
        return result_.toString();
    }
}
