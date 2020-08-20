
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "timeFormula"})
public class TimeFormulaRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal timeFormula;

    public TimeFormulaRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("timeFormula")
    public java.math.BigDecimal getTimeFormula() {
        return this.timeFormula;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("timeFormula")
    public void setTimeFormula(java.math.BigDecimal timeFormula) {
        this.timeFormula = timeFormula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeFormulaRuleOutput other = (TimeFormulaRuleOutput) o;
        if (this.getTimeFormula() != null ? !this.getTimeFormula().equals(other.getTimeFormula()) : other.getTimeFormula() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTimeFormula() != null ? this.getTimeFormula().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", timeFormula='%s'", timeFormula));
        result_.append(")");
        return result_.toString();
    }
}
