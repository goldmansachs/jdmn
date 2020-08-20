
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "sum"})
public class SumRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal sum;

    public SumRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sum")
    public java.math.BigDecimal getSum() {
        return this.sum;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("sum")
    public void setSum(java.math.BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SumRuleOutput other = (SumRuleOutput) o;
        if (this.getSum() != null ? !this.getSum().equals(other.getSum()) : other.getSum() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getSum() != null ? this.getSum().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", sum='%s'", sum));
        result_.append(")");
        return result_.toString();
    }
}
