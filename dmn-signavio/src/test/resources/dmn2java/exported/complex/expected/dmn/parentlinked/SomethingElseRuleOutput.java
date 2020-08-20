
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "somethingElse"})
public class SomethingElseRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private java.math.BigDecimal somethingElse;

    public SomethingElseRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("somethingElse")
    public java.math.BigDecimal getSomethingElse() {
        return this.somethingElse;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("somethingElse")
    public void setSomethingElse(java.math.BigDecimal somethingElse) {
        this.somethingElse = somethingElse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomethingElseRuleOutput other = (SomethingElseRuleOutput) o;
        if (this.getSomethingElse() != null ? !this.getSomethingElse().equals(other.getSomethingElse()) : other.getSomethingElse() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getSomethingElse() != null ? this.getSomethingElse().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", somethingElse='%s'", somethingElse));
        result_.append(")");
        return result_.toString();
    }
}
