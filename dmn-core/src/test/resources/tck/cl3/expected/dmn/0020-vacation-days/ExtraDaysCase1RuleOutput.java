
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ExtraDaysCase1"})
public class ExtraDaysCase1RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal extraDaysCase1;

    public ExtraDaysCase1RuleOutput(boolean matched) {
        super(matched);
    }

    public java.math.BigDecimal getExtraDaysCase1() {
        return this.extraDaysCase1;
    }
    public void setExtraDaysCase1(java.math.BigDecimal extraDaysCase1) {
        this.extraDaysCase1 = extraDaysCase1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtraDaysCase1RuleOutput other = (ExtraDaysCase1RuleOutput) o;
        if (this.getExtraDaysCase1() != null ? !this.getExtraDaysCase1().equals(other.getExtraDaysCase1()) : other.getExtraDaysCase1() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getExtraDaysCase1() != null ? this.getExtraDaysCase1().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", extraDaysCase1='%s'", extraDaysCase1));
        result_.append(")");
        return result_.toString();
    }
}
