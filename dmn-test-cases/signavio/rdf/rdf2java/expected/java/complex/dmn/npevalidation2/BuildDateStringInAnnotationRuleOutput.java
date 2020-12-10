
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "buildDateStringInAnnotation"})
public class BuildDateStringInAnnotationRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean allDefined;

    public BuildDateStringInAnnotationRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("allDefined")
    public Boolean getAllDefined() {
        return this.allDefined;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("allDefined")
    public void setAllDefined(Boolean allDefined) {
        this.allDefined = allDefined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildDateStringInAnnotationRuleOutput other = (BuildDateStringInAnnotationRuleOutput) o;
        if (this.getAllDefined() != null ? !this.getAllDefined().equals(other.getAllDefined()) : other.getAllDefined() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getAllDefined() != null ? this.getAllDefined().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", allDefined='%s'", allDefined));
        result_.append(")");
        return result_.toString();
    }
}
