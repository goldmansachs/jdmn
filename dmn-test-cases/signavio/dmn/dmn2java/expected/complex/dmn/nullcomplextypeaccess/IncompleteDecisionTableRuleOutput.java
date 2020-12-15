
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "incompleteDecisionTable"})
public class IncompleteDecisionTableRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String a;
    private java.math.BigDecimal b;

    public IncompleteDecisionTableRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("a")
    public String getA() {
        return this.a;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("a")
    public void setA(String a) {
        this.a = a;
    }
    @com.fasterxml.jackson.annotation.JsonGetter("b")
    public java.math.BigDecimal getB() {
        return this.b;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("b")
    public void setB(java.math.BigDecimal b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncompleteDecisionTableRuleOutput other = (IncompleteDecisionTableRuleOutput) o;
        if (this.getA() != null ? !this.getA().equals(other.getA()) : other.getA() != null) return false;
        if (this.getB() != null ? !this.getB().equals(other.getB()) : other.getB() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getA() != null ? this.getA().hashCode() : 0);
        result = 31 * result + (this.getB() != null ? this.getB().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", a='%s'", a));
        result_.append(String.format(", b='%s'", b));
        result_.append(")");
        return result_.toString();
    }
}
