
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ApprovalStatus"})
public class ApprovalStatusRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String approvalStatus;

    public ApprovalStatusRuleOutput(boolean matched) {
        super(matched);
    }

    public String getApprovalStatus() {
        return this.approvalStatus;
    }
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalStatusRuleOutput other = (ApprovalStatusRuleOutput) o;
        if (this.getApprovalStatus() != null ? !this.getApprovalStatus().equals(other.getApprovalStatus()) : other.getApprovalStatus() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getApprovalStatus() != null ? this.getApprovalStatus().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", approvalStatus='%s'", approvalStatus));
        result_.append(")");
        return result_.toString();
    }
}
