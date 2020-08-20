
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "doSomething"})
public class DoSomethingRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String doSomething;

    public DoSomethingRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("doSomething")
    public String getDoSomething() {
        return this.doSomething;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("doSomething")
    public void setDoSomething(String doSomething) {
        this.doSomething = doSomething;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoSomethingRuleOutput other = (DoSomethingRuleOutput) o;
        if (this.getDoSomething() != null ? !this.getDoSomething().equals(other.getDoSomething()) : other.getDoSomething() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getDoSomething() != null ? this.getDoSomething().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", doSomething='%s'", doSomething));
        result_.append(")");
        return result_.toString();
    }
}
