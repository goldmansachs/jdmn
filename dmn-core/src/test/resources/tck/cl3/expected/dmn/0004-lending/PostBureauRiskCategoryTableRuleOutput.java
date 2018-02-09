
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "PostBureauRiskCategoryTable"})
public class PostBureauRiskCategoryTableRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String postBureauRiskCategoryTable;

    public PostBureauRiskCategoryTableRuleOutput(boolean matched) {
        super(matched);
    }

    public String getPostBureauRiskCategoryTable() {
        return this.postBureauRiskCategoryTable;
    }
    public void setPostBureauRiskCategoryTable(String postBureauRiskCategoryTable) {
        this.postBureauRiskCategoryTable = postBureauRiskCategoryTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostBureauRiskCategoryTableRuleOutput other = (PostBureauRiskCategoryTableRuleOutput) o;
        if (this.getPostBureauRiskCategoryTable() != null ? !this.getPostBureauRiskCategoryTable().equals(other.getPostBureauRiskCategoryTable()) : other.getPostBureauRiskCategoryTable() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getPostBureauRiskCategoryTable() != null ? this.getPostBureauRiskCategoryTable().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", postBureauRiskCategoryTable='%s'", postBureauRiskCategoryTable));
        result_.append(")");
        return result_.toString();
    }
}
