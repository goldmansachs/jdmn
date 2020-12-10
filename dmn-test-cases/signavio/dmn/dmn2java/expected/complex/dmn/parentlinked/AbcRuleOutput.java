
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "abc"})
public class AbcRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal abc;

    public AbcRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("abc")
    public java.math.BigDecimal getAbc() {
        return this.abc;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("abc")
    public void setAbc(java.math.BigDecimal abc) {
        this.abc = abc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbcRuleOutput other = (AbcRuleOutput) o;
        if (this.getAbc() != null ? !this.getAbc().equals(other.getAbc()) : other.getAbc() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getAbc() != null ? this.getAbc().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", abc='%s'", abc));
        result_.append(")");
        return result_.toString();
    }
}
