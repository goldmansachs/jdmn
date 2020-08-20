
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "datetimeOperators"})
public class DatetimeOperatorsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String datetimeOperators;

    public DatetimeOperatorsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("datetimeOperators")
    public String getDatetimeOperators() {
        return this.datetimeOperators;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("datetimeOperators")
    public void setDatetimeOperators(String datetimeOperators) {
        this.datetimeOperators = datetimeOperators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatetimeOperatorsRuleOutput other = (DatetimeOperatorsRuleOutput) o;
        if (this.getDatetimeOperators() != null ? !this.getDatetimeOperators().equals(other.getDatetimeOperators()) : other.getDatetimeOperators() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDatetimeOperators() != null ? this.getDatetimeOperators().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", datetimeOperators='%s'", datetimeOperators));
        result_.append(")");
        return result_.toString();
    }
}
