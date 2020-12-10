
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dateFormula"})
public class DateFormulaRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal dateFormula;

    public DateFormulaRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateFormula")
    public java.math.BigDecimal getDateFormula() {
        return this.dateFormula;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateFormula")
    public void setDateFormula(java.math.BigDecimal dateFormula) {
        this.dateFormula = dateFormula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateFormulaRuleOutput other = (DateFormulaRuleOutput) o;
        if (this.getDateFormula() != null ? !this.getDateFormula().equals(other.getDateFormula()) : other.getDateFormula() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDateFormula() != null ? this.getDateFormula().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dateFormula='%s'", dateFormula));
        result_.append(")");
        return result_.toString();
    }
}
