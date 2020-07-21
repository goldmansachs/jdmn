
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "assessApplicantAge"})
public class AssessApplicantAgeRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal assessApplicantAge;

    public AssessApplicantAgeRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("assessApplicantAge")
    public java.math.BigDecimal getAssessApplicantAge() {
        return this.assessApplicantAge;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("assessApplicantAge")
    public void setAssessApplicantAge(java.math.BigDecimal assessApplicantAge) {
        this.assessApplicantAge = assessApplicantAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssessApplicantAgeRuleOutput other = (AssessApplicantAgeRuleOutput) o;
        if (this.getAssessApplicantAge() != null ? !this.getAssessApplicantAge().equals(other.getAssessApplicantAge()) : other.getAssessApplicantAge() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getAssessApplicantAge() != null ? this.getAssessApplicantAge().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", assessApplicantAge='%s'", assessApplicantAge));
        result_.append(")");
        return result_.toString();
    }
}
