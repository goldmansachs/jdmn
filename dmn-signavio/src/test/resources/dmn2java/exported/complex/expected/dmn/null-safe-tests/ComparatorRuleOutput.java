
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "comparator"})
public class ComparatorRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String comparator;

    public ComparatorRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("comparator")
    public String getComparator() {
        return this.comparator;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("comparator")
    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparatorRuleOutput other = (ComparatorRuleOutput) o;
        if (this.getComparator() != null ? !this.getComparator().equals(other.getComparator()) : other.getComparator() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getComparator() != null ? this.getComparator().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", comparator='%s'", comparator));
        result_.append(")");
        return result_.toString();
    }
}
