
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "compareAgainstLendingThreshold"})
public class CompareAgainstLendingThresholdRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal compareAgainstLendingThreshold;

    public CompareAgainstLendingThresholdRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("compareAgainstLendingThreshold")
    public java.math.BigDecimal getCompareAgainstLendingThreshold() {
        return this.compareAgainstLendingThreshold;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("compareAgainstLendingThreshold")
    public void setCompareAgainstLendingThreshold(java.math.BigDecimal compareAgainstLendingThreshold) {
        this.compareAgainstLendingThreshold = compareAgainstLendingThreshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompareAgainstLendingThresholdRuleOutput other = (CompareAgainstLendingThresholdRuleOutput) o;
        if (this.getCompareAgainstLendingThreshold() != null ? !this.getCompareAgainstLendingThreshold().equals(other.getCompareAgainstLendingThreshold()) : other.getCompareAgainstLendingThreshold() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getCompareAgainstLendingThreshold() != null ? this.getCompareAgainstLendingThreshold().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", compareAgainstLendingThreshold='%s'", compareAgainstLendingThreshold));
        result_.append(")");
        return result_.toString();
    }
}
