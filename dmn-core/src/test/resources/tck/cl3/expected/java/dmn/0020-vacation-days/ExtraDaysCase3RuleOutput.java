
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ExtraDaysCase3"})
public class ExtraDaysCase3RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal extraDaysCase3;

    public ExtraDaysCase3RuleOutput(boolean matched) {
        super(matched);
    }

    public java.math.BigDecimal getExtraDaysCase3() {
        return this.extraDaysCase3;
    }
    public void setExtraDaysCase3(java.math.BigDecimal extraDaysCase3) {
        this.extraDaysCase3 = extraDaysCase3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtraDaysCase3RuleOutput other = (ExtraDaysCase3RuleOutput) o;
        if (this.getExtraDaysCase3() != null ? !this.getExtraDaysCase3().equals(other.getExtraDaysCase3()) : other.getExtraDaysCase3() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getExtraDaysCase3() != null ? this.getExtraDaysCase3().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", extraDaysCase3='%s'", extraDaysCase3));
        result_.append(")");
        return result_.toString();
    }
}
