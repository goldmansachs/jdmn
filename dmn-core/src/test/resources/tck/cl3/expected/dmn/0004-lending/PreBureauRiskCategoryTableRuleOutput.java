
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "PreBureauRiskCategoryTable"})
public class PreBureauRiskCategoryTableRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String preBureauRiskCategoryTable;

    public PreBureauRiskCategoryTableRuleOutput(boolean matched) {
        super(matched);
    }

    public String getPreBureauRiskCategoryTable() {
        return this.preBureauRiskCategoryTable;
    }
    public void setPreBureauRiskCategoryTable(String preBureauRiskCategoryTable) {
        this.preBureauRiskCategoryTable = preBureauRiskCategoryTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreBureauRiskCategoryTableRuleOutput other = (PreBureauRiskCategoryTableRuleOutput) o;
        if (this.getPreBureauRiskCategoryTable() != null ? !this.getPreBureauRiskCategoryTable().equals(other.getPreBureauRiskCategoryTable()) : other.getPreBureauRiskCategoryTable() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPreBureauRiskCategoryTable() != null ? this.getPreBureauRiskCategoryTable().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", preBureauRiskCategoryTable='%s'", preBureauRiskCategoryTable));
        result_.append(")");
        return result_.toString();
    }
}
