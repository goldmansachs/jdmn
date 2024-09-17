package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "temporalDiffs"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TemporalDiffsImpl implements TemporalDiffs {
        private java.lang.Number dateDiff;
        private java.lang.Number dateTimeDiff;

    public TemporalDiffsImpl() {
    }

    public TemporalDiffsImpl(java.lang.Number dateDiff, java.lang.Number dateTimeDiff) {
        this.setDateDiff(dateDiff);
        this.setDateTimeDiff(dateTimeDiff);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateDiff")
    public java.lang.Number getDateDiff() {
        return this.dateDiff;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateDiff")
    public void setDateDiff(java.lang.Number dateDiff) {
        this.dateDiff = dateDiff;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiff")
    public java.lang.Number getDateTimeDiff() {
        return this.dateTimeDiff;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeDiff")
    public void setDateTimeDiff(java.lang.Number dateTimeDiff) {
        this.dateTimeDiff = dateTimeDiff;
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
