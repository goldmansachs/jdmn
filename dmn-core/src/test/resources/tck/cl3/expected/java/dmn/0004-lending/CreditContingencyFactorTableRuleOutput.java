
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "CreditContingencyFactorTable"})
public class CreditContingencyFactorTableRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal creditContingencyFactorTable;

    public CreditContingencyFactorTableRuleOutput(boolean matched) {
        super(matched);
    }

    public java.math.BigDecimal getCreditContingencyFactorTable() {
        return this.creditContingencyFactorTable;
    }
    public void setCreditContingencyFactorTable(java.math.BigDecimal creditContingencyFactorTable) {
        this.creditContingencyFactorTable = creditContingencyFactorTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditContingencyFactorTableRuleOutput other = (CreditContingencyFactorTableRuleOutput) o;
        if (this.getCreditContingencyFactorTable() != null ? !this.getCreditContingencyFactorTable().equals(other.getCreditContingencyFactorTable()) : other.getCreditContingencyFactorTable() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getCreditContingencyFactorTable() != null ? this.getCreditContingencyFactorTable().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", creditContingencyFactorTable='%s'", creditContingencyFactorTable));
        result_.append(")");
        return result_.toString();
    }
}
