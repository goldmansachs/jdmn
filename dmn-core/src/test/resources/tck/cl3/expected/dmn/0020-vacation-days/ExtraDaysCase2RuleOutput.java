
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ExtraDaysCase2"})
public class ExtraDaysCase2RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal extraDaysCase2;

    public ExtraDaysCase2RuleOutput(boolean matched) {
        super(matched);
    }

    public java.math.BigDecimal getExtraDaysCase2() {
        return this.extraDaysCase2;
    }
    public void setExtraDaysCase2(java.math.BigDecimal extraDaysCase2) {
        this.extraDaysCase2 = extraDaysCase2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtraDaysCase2RuleOutput other = (ExtraDaysCase2RuleOutput) o;
        if (this.getExtraDaysCase2() != null ? !this.getExtraDaysCase2().equals(other.getExtraDaysCase2()) : other.getExtraDaysCase2() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getExtraDaysCase2() != null ? this.getExtraDaysCase2().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", extraDaysCase2='%s'", extraDaysCase2));
        result_.append(")");
        return result_.toString();
    }
}
