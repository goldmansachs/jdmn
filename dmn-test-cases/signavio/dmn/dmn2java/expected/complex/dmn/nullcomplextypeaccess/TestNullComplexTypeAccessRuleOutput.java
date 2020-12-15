
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "testNullComplexTypeAccess"})
public class TestNullComplexTypeAccessRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private String testNullComplexTypeAccess;

    public TestNullComplexTypeAccessRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("testNullComplexTypeAccess")
    public String getTestNullComplexTypeAccess() {
        return this.testNullComplexTypeAccess;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("testNullComplexTypeAccess")
    public void setTestNullComplexTypeAccess(String testNullComplexTypeAccess) {
        this.testNullComplexTypeAccess = testNullComplexTypeAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestNullComplexTypeAccessRuleOutput other = (TestNullComplexTypeAccessRuleOutput) o;
        if (this.getTestNullComplexTypeAccess() != null ? !this.getTestNullComplexTypeAccess().equals(other.getTestNullComplexTypeAccess()) : other.getTestNullComplexTypeAccess() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getTestNullComplexTypeAccess() != null ? this.getTestNullComplexTypeAccess().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", testNullComplexTypeAccess='%s'", testNullComplexTypeAccess));
        result_.append(")");
        return result_.toString();
    }
}
