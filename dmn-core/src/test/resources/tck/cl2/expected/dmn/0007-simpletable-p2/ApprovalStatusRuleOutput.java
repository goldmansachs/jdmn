
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "ApprovalStatus"})
public class ApprovalStatusRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String approvalStatus;
    private Integer approvalStatusPriority;

    public ApprovalStatusRuleOutput(boolean matched) {
        super(matched);
    }

    public String getApprovalStatus() {
        return this.approvalStatus;
    }
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getApprovalStatusPriority() {
        return this.approvalStatusPriority;
    }
    public void setApprovalStatusPriority(Integer approvalStatusPriority) {
        this.approvalStatusPriority = approvalStatusPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalStatusRuleOutput other = (ApprovalStatusRuleOutput) o;
        if (this.getApprovalStatus() != null ? !this.getApprovalStatus().equals(other.getApprovalStatus()) : other.getApprovalStatus() != null) return false;
        if (this.getApprovalStatusPriority() != null ? !this.getApprovalStatusPriority().equals(other.getApprovalStatusPriority()) : other.getApprovalStatusPriority() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getApprovalStatus() != null ? this.getApprovalStatus().hashCode() : 0);
        result = 31 * result + (this.getApprovalStatusPriority() != null ? this.getApprovalStatusPriority().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", approvalStatus='%s'", approvalStatus));
        result_.append(")");
        return result_.toString();
    }

    @Override
    public List<com.gs.dmn.runtime.RuleOutput> sort(List<com.gs.dmn.runtime.RuleOutput> matchedResults_) {
        List<com.gs.dmn.runtime.Pair<String, Integer>> approvalStatusPairs = new ArrayList<>();
        matchedResults_.forEach(matchedResult_ -> {
            approvalStatusPairs.add(new com.gs.dmn.runtime.Pair(((ApprovalStatusRuleOutput)matchedResult_).getApprovalStatus(), ((ApprovalStatusRuleOutput)matchedResult_).getApprovalStatusPriority()));
        });
        approvalStatusPairs.sort(new com.gs.dmn.runtime.PairComparator());

        List<com.gs.dmn.runtime.RuleOutput> result_ = new ArrayList<com.gs.dmn.runtime.RuleOutput>();
        for(int i=0; i<matchedResults_.size(); i++) {
            ApprovalStatusRuleOutput output_ = new ApprovalStatusRuleOutput(true);
            output_.setApprovalStatus(approvalStatusPairs.get(i).getLeft());
            output_.setApprovalStatusPriority(approvalStatusPairs.get(i).getRight());
            result_.add(output_);
        }
        return result_;
    }
}
