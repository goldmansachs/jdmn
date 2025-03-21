package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.ZipImpl.class)
public interface Zip extends com.gs.dmn.runtime.DMNType {
    static Zip toZip(Object other) {
        if (other == null) {
            return null;
        } else if (Zip.class.isAssignableFrom(other.getClass())) {
            return (Zip)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ZipImpl result_ = new ZipImpl();
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("names") || ((com.gs.dmn.runtime.Context)other).keySet().contains("names")) {
                result_.setNames((String)((com.gs.dmn.runtime.Context)other).get("names", "names"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("ages") || ((com.gs.dmn.runtime.Context)other).keySet().contains("ages")) {
                result_.setAges((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("ages", "ages"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("dateDiffs") || ((com.gs.dmn.runtime.Context)other).keySet().contains("dateDiffs")) {
                result_.setDateDiffs((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("dateDiffs", "dateDiffs"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("dateTimeDiffs") || ((com.gs.dmn.runtime.Context)other).keySet().contains("dateTimeDiffs")) {
                result_.setDateTimeDiffs((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("dateTimeDiffs", "dateTimeDiffs"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("temporalUnits") || ((com.gs.dmn.runtime.Context)other).keySet().contains("temporalUnits")) {
                result_.setTemporalUnits((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("temporalUnits", "temporalUnits"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("agesListDescription") || ((com.gs.dmn.runtime.Context)other).keySet().contains("agesListDescription")) {
                result_.setAgesListDescription((String)((com.gs.dmn.runtime.Context)other).get("agesListDescription", "agesListDescription"));
            } else {
                return  null;
            }
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toZip(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Zip.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("names")
    String getNames();

    @com.fasterxml.jackson.annotation.JsonGetter("ages")
    java.lang.Number getAges();

    @com.fasterxml.jackson.annotation.JsonGetter("dateDiffs")
    java.lang.Number getDateDiffs();

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiffs")
    java.lang.Number getDateTimeDiffs();

    @com.fasterxml.jackson.annotation.JsonGetter("temporalUnits")
    java.lang.Number getTemporalUnits();

    @com.fasterxml.jackson.annotation.JsonGetter("agesListDescription")
    String getAgesListDescription();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("names", getNames());
        context.put("ages", getAges());
        context.put("dateDiffs", getDateDiffs());
        context.put("dateTimeDiffs", getDateTimeDiffs());
        context.put("temporalUnits", getTemporalUnits());
        context.put("agesListDescription", getAgesListDescription());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zip other = (Zip) o;
        if (this.getAges() != null ? !this.getAges().equals(other.getAges()) : other.getAges() != null) return false;
        if (this.getAgesListDescription() != null ? !this.getAgesListDescription().equals(other.getAgesListDescription()) : other.getAgesListDescription() != null) return false;
        if (this.getDateDiffs() != null ? !this.getDateDiffs().equals(other.getDateDiffs()) : other.getDateDiffs() != null) return false;
        if (this.getDateTimeDiffs() != null ? !this.getDateTimeDiffs().equals(other.getDateTimeDiffs()) : other.getDateTimeDiffs() != null) return false;
        if (this.getNames() != null ? !this.getNames().equals(other.getNames()) : other.getNames() != null) return false;
        if (this.getTemporalUnits() != null ? !this.getTemporalUnits().equals(other.getTemporalUnits()) : other.getTemporalUnits() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAges() != null ? this.getAges().hashCode() : 0);
        result = 31 * result + (this.getAgesListDescription() != null ? this.getAgesListDescription().hashCode() : 0);
        result = 31 * result + (this.getDateDiffs() != null ? this.getDateDiffs().hashCode() : 0);
        result = 31 * result + (this.getDateTimeDiffs() != null ? this.getDateTimeDiffs().hashCode() : 0);
        result = 31 * result + (this.getNames() != null ? this.getNames().hashCode() : 0);
        result = 31 * result + (this.getTemporalUnits() != null ? this.getTemporalUnits().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("ages=" + getAges());
        result_.append(", agesListDescription=" + getAgesListDescription());
        result_.append(", dateDiffs=" + getDateDiffs());
        result_.append(", dateTimeDiffs=" + getDateTimeDiffs());
        result_.append(", names=" + getNames());
        result_.append(", temporalUnits=" + getTemporalUnits());
        result_.append("}");
        return result_.toString();
    }
}
