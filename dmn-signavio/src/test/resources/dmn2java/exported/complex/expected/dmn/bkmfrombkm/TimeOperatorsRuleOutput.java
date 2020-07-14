
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "timeOperators"})
public class TimeOperatorsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String timeOperators;

    public TimeOperatorsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("timeOperators")
    public String getTimeOperators() {
        return this.timeOperators;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("timeOperators")
    public void setTimeOperators(String timeOperators) {
        this.timeOperators = timeOperators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeOperatorsRuleOutput other = (TimeOperatorsRuleOutput) o;
        if (this.getTimeOperators() != null ? !this.getTimeOperators().equals(other.getTimeOperators()) : other.getTimeOperators() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTimeOperators() != null ? this.getTimeOperators().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", timeOperators='%s'", timeOperators));
        result_.append(")");
        return result_.toString();
    }
}
