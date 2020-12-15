
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "temporalComparator"})
public class TemporalComparatorRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String temporalComparator;

    public TemporalComparatorRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("temporalComparator")
    public String getTemporalComparator() {
        return this.temporalComparator;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("temporalComparator")
    public void setTemporalComparator(String temporalComparator) {
        this.temporalComparator = temporalComparator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemporalComparatorRuleOutput other = (TemporalComparatorRuleOutput) o;
        if (this.getTemporalComparator() != null ? !this.getTemporalComparator().equals(other.getTemporalComparator()) : other.getTemporalComparator() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTemporalComparator() != null ? this.getTemporalComparator().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", temporalComparator='%s'", temporalComparator));
        result_.append(")");
        return result_.toString();
    }
}
