
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "datetimeFormula"})
public class DatetimeFormulaRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal datetimeFormula;

    public DatetimeFormulaRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("datetimeFormula")
    public java.math.BigDecimal getDatetimeFormula() {
        return this.datetimeFormula;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("datetimeFormula")
    public void setDatetimeFormula(java.math.BigDecimal datetimeFormula) {
        this.datetimeFormula = datetimeFormula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatetimeFormulaRuleOutput other = (DatetimeFormulaRuleOutput) o;
        if (this.getDatetimeFormula() != null ? !this.getDatetimeFormula().equals(other.getDatetimeFormula()) : other.getDatetimeFormula() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDatetimeFormula() != null ? this.getDatetimeFormula().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", datetimeFormula='%s'", datetimeFormula));
        result_.append(")");
        return result_.toString();
    }
}
