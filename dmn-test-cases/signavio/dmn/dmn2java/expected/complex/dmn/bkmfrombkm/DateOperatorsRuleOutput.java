
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dateOperators"})
public class DateOperatorsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String dateOperators;

    public DateOperatorsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateOperators")
    public String getDateOperators() {
        return this.dateOperators;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateOperators")
    public void setDateOperators(String dateOperators) {
        this.dateOperators = dateOperators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateOperatorsRuleOutput other = (DateOperatorsRuleOutput) o;
        if (this.getDateOperators() != null ? !this.getDateOperators().equals(other.getDateOperators()) : other.getDateOperators() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDateOperators() != null ? this.getDateOperators().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dateOperators='%s'", dateOperators));
        result_.append(")");
        return result_.toString();
    }
}
