
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "temporalDiffs"})
public class TemporalDiffsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal dateDiff;
    private java.math.BigDecimal dateTimeDiff;

    public TemporalDiffsRuleOutput(boolean matched) {
        super(matched);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemporalDiffsRuleOutput other = (TemporalDiffsRuleOutput) o;
        if (this.getDateDiff() != null ? !this.getDateDiff().equals(other.getDateDiff()) : other.getDateDiff() != null) return false;
        if (this.getDateTimeDiff() != null ? !this.getDateTimeDiff().equals(other.getDateTimeDiff()) : other.getDateTimeDiff() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDateDiff() != null ? this.getDateDiff().hashCode() : 0);
        result = 31 * result + (this.getDateTimeDiff() != null ? this.getDateTimeDiff().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dateDiff='%s'", dateDiff));
        result_.append(String.format(", dateTimeDiff='%s'", dateTimeDiff));
        result_.append(")");
        return result_.toString();
    }
}
