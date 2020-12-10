
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "dependentDecision2"})
public class DependentDecision2RuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String dD2O1;
    private String dD2O2;

    public DependentDecision2RuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dD2O1")
    public String getDD2O1() {
        return this.dD2O1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dD2O1")
    public void setDD2O1(String dD2O1) {
        this.dD2O1 = dD2O1;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("dD2O2")
    public String getDD2O2() {
        return this.dD2O2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dD2O2")
    public void setDD2O2(String dD2O2) {
        this.dD2O2 = dD2O2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DependentDecision2RuleOutput other = (DependentDecision2RuleOutput) o;
        if (this.getDD2O1() != null ? !this.getDD2O1().equals(other.getDD2O1()) : other.getDD2O1() != null) return false;
        if (this.getDD2O2() != null ? !this.getDD2O2().equals(other.getDD2O2()) : other.getDD2O2() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDD2O1() != null ? this.getDD2O1().hashCode() : 0);
        result = 31 * result + (this.getDD2O2() != null ? this.getDD2O2().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", dD2O1='%s'", dD2O1));
        result_.append(String.format(", dD2O2='%s'", dD2O2));
        result_.append(")");
        return result_.toString();
    }
}
