
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "test"})
public class TestRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String test;

    public TestRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("test")
    public String getTest() {
        return this.test;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("test")
    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestRuleOutput other = (TestRuleOutput) o;
        if (this.getTest() != null ? !this.getTest().equals(other.getTest()) : other.getTest() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTest() != null ? this.getTest().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", test='%s'", test));
        result_.append(")");
        return result_.toString();
    }
}
