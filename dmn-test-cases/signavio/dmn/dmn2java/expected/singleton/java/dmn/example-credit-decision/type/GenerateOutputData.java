package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "generateOutputData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.GenerateOutputDataImpl.class)
public interface GenerateOutputData extends com.gs.dmn.runtime.DMNType {
    static GenerateOutputData toGenerateOutputData(Object other) {
        if (other == null) {
            return null;
        } else if (GenerateOutputData.class.isAssignableFrom(other.getClass())) {
            return (GenerateOutputData)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            GenerateOutputDataImpl result_ = new GenerateOutputDataImpl();
            result_.setDecision((String)((com.gs.dmn.runtime.Context)other).get("decision", "Decision"));
            result_.setAssessment((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("assessment", "Assessment"));
            result_.setIssue((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("issue", "Issue"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toGenerateOutputData(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), GenerateOutputData.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Decision")
    String getDecision();

    @com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    java.math.BigDecimal getAssessment();

    @com.fasterxml.jackson.annotation.JsonGetter("Issue")
    java.math.BigDecimal getIssue();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("decision", getDecision());
        context.put("assessment", getAssessment());
        context.put("issue", getIssue());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenerateOutputData other = (GenerateOutputData) o;
        if (this.getAssessment() != null ? !this.getAssessment().equals(other.getAssessment()) : other.getAssessment() != null) return false;
        if (this.getDecision() != null ? !this.getDecision().equals(other.getDecision()) : other.getDecision() != null) return false;
        if (this.getIssue() != null ? !this.getIssue().equals(other.getIssue()) : other.getIssue() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAssessment() != null ? this.getAssessment().hashCode() : 0);
        result = 31 * result + (this.getDecision() != null ? this.getDecision().hashCode() : 0);
        result = 31 * result + (this.getIssue() != null ? this.getIssue().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Assessment=" + getAssessment());
        result_.append(", Decision=" + getDecision());
        result_.append(", Issue=" + getIssue());
        result_.append("}");
        return result_.toString();
    }
}
