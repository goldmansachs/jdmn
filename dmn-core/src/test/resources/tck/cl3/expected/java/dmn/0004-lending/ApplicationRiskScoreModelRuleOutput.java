
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ApplicationRiskScoreModel"})
public class ApplicationRiskScoreModelRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal applicationRiskScoreModel;

    public ApplicationRiskScoreModelRuleOutput(boolean matched) {
        super(matched);
    }

    public java.math.BigDecimal getApplicationRiskScoreModel() {
        return this.applicationRiskScoreModel;
    }
    public void setApplicationRiskScoreModel(java.math.BigDecimal applicationRiskScoreModel) {
        this.applicationRiskScoreModel = applicationRiskScoreModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationRiskScoreModelRuleOutput other = (ApplicationRiskScoreModelRuleOutput) o;
        if (this.getApplicationRiskScoreModel() != null ? !this.getApplicationRiskScoreModel().equals(other.getApplicationRiskScoreModel()) : other.getApplicationRiskScoreModel() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getApplicationRiskScoreModel() != null ? this.getApplicationRiskScoreModel().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", applicationRiskScoreModel='%s'", applicationRiskScoreModel));
        result_.append(")");
        return result_.toString();
    }
}
