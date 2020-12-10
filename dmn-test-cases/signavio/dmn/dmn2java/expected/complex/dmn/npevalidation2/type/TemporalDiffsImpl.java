package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "temporalDiffs"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TemporalDiffsImpl implements TemporalDiffs {
        private java.math.BigDecimal dateDiff;
        private java.math.BigDecimal dateTimeDiff;

    public TemporalDiffsImpl() {
    }

    public TemporalDiffsImpl(java.math.BigDecimal dateDiff, java.math.BigDecimal dateTimeDiff) {
        this.setDateDiff(dateDiff);
        this.setDateTimeDiff(dateTimeDiff);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateDiff")
    public java.math.BigDecimal getDateDiff() {
        return this.dateDiff;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateDiff")
    public void setDateDiff(java.math.BigDecimal dateDiff) {
        this.dateDiff = dateDiff;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeDiff")
    public java.math.BigDecimal getDateTimeDiff() {
        return this.dateTimeDiff;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeDiff")
    public void setDateTimeDiff(java.math.BigDecimal dateTimeDiff) {
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
