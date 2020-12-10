
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "accessCertainTemporalUnits"})
public class AccessCertainTemporalUnitsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal value;

    public AccessCertainTemporalUnitsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("value")
    public java.math.BigDecimal getValue() {
        return this.value;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("value")
    public void setValue(java.math.BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessCertainTemporalUnitsRuleOutput other = (AccessCertainTemporalUnitsRuleOutput) o;
        if (this.getValue() != null ? !this.getValue().equals(other.getValue()) : other.getValue() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getValue() != null ? this.getValue().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", value='%s'", value));
        result_.append(")");
        return result_.toString();
    }
}
