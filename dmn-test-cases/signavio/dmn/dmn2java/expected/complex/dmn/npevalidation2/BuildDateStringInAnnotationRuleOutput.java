
import java.util.*;

@javax.annotation.Generated(value = {"decisionTableRuleOutput.ftl", "buildDateStringInAnnotation"})
public class BuildDateStringInAnnotationRuleOutput extends com.gs.dmn.runtime.RuleOutput {
    private Boolean buildDateStringInAnnotation;

    public BuildDateStringInAnnotationRuleOutput(boolean matched) {
        super(matched);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("buildDateStringInAnnotation")
    public Boolean getBuildDateStringInAnnotation() {
        return this.buildDateStringInAnnotation;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("buildDateStringInAnnotation")
    public void setBuildDateStringInAnnotation(Boolean buildDateStringInAnnotation) {
        this.buildDateStringInAnnotation = buildDateStringInAnnotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildDateStringInAnnotationRuleOutput other = (BuildDateStringInAnnotationRuleOutput) o;
        if (this.getBuildDateStringInAnnotation() != null ? !this.getBuildDateStringInAnnotation().equals(other.getBuildDateStringInAnnotation()) : other.getBuildDateStringInAnnotation() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.getBuildDateStringInAnnotation() != null ? this.getBuildDateStringInAnnotation().hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuilder result_ = new StringBuilder("(matched=" + isMatched());
        result_.append(String.format(", buildDateStringInAnnotation='%s'", buildDateStringInAnnotation));
        result_.append(")");
        return result_.toString();
    }
}
