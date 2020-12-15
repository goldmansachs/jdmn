
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "isForexRateRequired"})
public class IsForexRateRequiredRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean isForexRateRequired;

    public IsForexRateRequiredRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("isForexRateRequired")
    public Boolean getIsForexRateRequired() {
        return this.isForexRateRequired;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("isForexRateRequired")
    public void setIsForexRateRequired(Boolean isForexRateRequired) {
        this.isForexRateRequired = isForexRateRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IsForexRateRequiredRuleOutput other = (IsForexRateRequiredRuleOutput) o;
        if (this.getIsForexRateRequired() != null ? !this.getIsForexRateRequired().equals(other.getIsForexRateRequired()) : other.getIsForexRateRequired() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getIsForexRateRequired() != null ? this.getIsForexRateRequired().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", isForexRateRequired='%s'", isForexRateRequired));
        result_.append(")");
        return result_.toString();
    }
}
