
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "accessCertainTemporalUnits"})
public class AccessCertainTemporalUnitsRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal accessCertainTemporalUnits;

    public AccessCertainTemporalUnitsRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("accessCertainTemporalUnits")
    public java.math.BigDecimal getAccessCertainTemporalUnits() {
        return this.accessCertainTemporalUnits;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("accessCertainTemporalUnits")
    public void setAccessCertainTemporalUnits(java.math.BigDecimal accessCertainTemporalUnits) {
        this.accessCertainTemporalUnits = accessCertainTemporalUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessCertainTemporalUnitsRuleOutput other = (AccessCertainTemporalUnitsRuleOutput) o;
        if (this.getAccessCertainTemporalUnits() != null ? !this.getAccessCertainTemporalUnits().equals(other.getAccessCertainTemporalUnits()) : other.getAccessCertainTemporalUnits() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getAccessCertainTemporalUnits() != null ? this.getAccessCertainTemporalUnits().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", accessCertainTemporalUnits='%s'", accessCertainTemporalUnits));
        result_.append(")");
        return result_.toString();
    }
}
