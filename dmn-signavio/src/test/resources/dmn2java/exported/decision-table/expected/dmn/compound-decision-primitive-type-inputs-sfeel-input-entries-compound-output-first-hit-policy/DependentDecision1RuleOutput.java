
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dependentDecision1"})
public class DependentDecision1RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String dD1O1;
    private String dD1O2;

    public DependentDecision1RuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dD1O1")
    public String getDD1O1() {
        return this.dD1O1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dD1O1")
    public void setDD1O1(String dD1O1) {
        this.dD1O1 = dD1O1;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("dD1O2")
    public String getDD1O2() {
        return this.dD1O2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dD1O2")
    public void setDD1O2(String dD1O2) {
        this.dD1O2 = dD1O2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DependentDecision1RuleOutput other = (DependentDecision1RuleOutput) o;
        if (this.getDD1O1() != null ? !this.getDD1O1().equals(other.getDD1O1()) : other.getDD1O1() != null) return false;
        if (this.getDD1O2() != null ? !this.getDD1O2().equals(other.getDD1O2()) : other.getDD1O2() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDD1O1() != null ? this.getDD1O1().hashCode() : 0);
        result = 31 * result + (this.getDD1O2() != null ? this.getDD1O2().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dD1O1='%s'", dD1O1));
        result_.append(String.format(", dD1O2='%s'", dD1O2));
        result_.append(")");
        return result_.toString();
    }
}
