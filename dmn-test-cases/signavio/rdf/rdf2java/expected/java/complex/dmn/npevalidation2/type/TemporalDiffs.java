package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "temporalDiffs"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TemporalDiffsImpl.class)
public interface TemporalDiffs extends com.gs.dmn.runtime.DMNType {
    static TemporalDiffs toTemporalDiffs(Object other) {
        if (other == null) {
            return null;
        } else if (TemporalDiffs.class.isAssignableFrom(other.getClass())) {
            return (TemporalDiffs)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TemporalDiffsImpl result_ = new TemporalDiffsImpl();
            result_.setDateDiff((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("dateDiff", "dateDiff"));
            result_.setDateTimeDiff((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("dateTimeDiff", "dateTimeDiff"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTemporalDiffs(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TemporalDiffs.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateDiff")
    java.math.BigDecimal getDateDiff();

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiff")
    java.math.BigDecimal getDateTimeDiff();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("dateDiff", getDateDiff());
        context.put("dateTimeDiff", getDateTimeDiff());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemporalDiffs other = (TemporalDiffs) o;
        if (this.getDateDiff() != null ? !this.getDateDiff().equals(other.getDateDiff()) : other.getDateDiff() != null) return false;
        if (this.getDateTimeDiff() != null ? !this.getDateTimeDiff().equals(other.getDateTimeDiff()) : other.getDateTimeDiff() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDateDiff() != null ? this.getDateDiff().hashCode() : 0);
        result = 31 * result + (this.getDateTimeDiff() != null ? this.getDateTimeDiff().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("dateDiff=" + getDateDiff());
        result_.append(", dateTimeDiff=" + getDateTimeDiff());
        result_.append("}");
        return result_.toString();
    }
}
