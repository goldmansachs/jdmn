
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "compareLists"})
public class CompareListsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal compareLists;

    public CompareListsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("compareLists")
    public java.math.BigDecimal getCompareLists() {
        return this.compareLists;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("compareLists")
    public void setCompareLists(java.math.BigDecimal compareLists) {
        this.compareLists = compareLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompareListsRuleOutput other = (CompareListsRuleOutput) o;
        if (this.getCompareLists() != null ? !this.getCompareLists().equals(other.getCompareLists()) : other.getCompareLists() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getCompareLists() != null ? this.getCompareLists().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", compareLists='%s'", compareLists));
        result_.append(")");
        return result_.toString();
    }
}
