
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "Approval"})
public class ApprovalRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String status;
    private String rate;

    public ApprovalRuleOutput(boolean matched) {
        super(matched);
    }

    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRate() {
        return this.rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalRuleOutput other = (ApprovalRuleOutput) o;
        if (this.getStatus() != null ? !this.getStatus().equals(other.getStatus()) : other.getStatus() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getStatus() != null ? this.getStatus().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", status='%s'", status));
        result_.append(String.format(", rate='%s'", rate));
        result_.append(")");
        return result_.toString();
    }
}
