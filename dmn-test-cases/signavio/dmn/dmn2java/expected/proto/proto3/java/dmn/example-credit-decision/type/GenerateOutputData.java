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
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("decision") || ((com.gs.dmn.runtime.Context)other).keySet().contains("Decision")) {
                result_.setDecision((String)((com.gs.dmn.runtime.Context)other).get("decision", "Decision"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("assessment") || ((com.gs.dmn.runtime.Context)other).keySet().contains("Assessment")) {
                result_.setAssessment((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("assessment", "Assessment"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("issue") || ((com.gs.dmn.runtime.Context)other).keySet().contains("Issue")) {
                result_.setIssue((java.lang.Number)((com.gs.dmn.runtime.Context)other).get("issue", "Issue"));
            } else {
                return  null;
            }
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toGenerateOutputData(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else if (other instanceof proto.GenerateOutputData) {
            GenerateOutputDataImpl result_ = new GenerateOutputDataImpl();
            result_.setDecision(((proto.GenerateOutputData) other).getDecision());
            result_.setAssessment(((java.lang.Number) java.math.BigDecimal.valueOf(((proto.GenerateOutputData) other).getAssessment())));
            result_.setIssue(((java.lang.Number) java.math.BigDecimal.valueOf(((proto.GenerateOutputData) other).getIssue())));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), GenerateOutputData.class.getSimpleName()));
        }
    }

    static proto.GenerateOutputData toProto(GenerateOutputData other) {
        proto.GenerateOutputData.Builder result_ = proto.GenerateOutputData.newBuilder();
        if (other != null) {
            String decisionProto_ = (((GenerateOutputData) other).getDecision() == null ? "" : ((GenerateOutputData) other).getDecision());
            result_.setDecision(decisionProto_);
            Double assessmentProto_ = (((GenerateOutputData) other).getAssessment() == null ? 0.0 : ((GenerateOutputData) other).getAssessment().doubleValue());
            result_.setAssessment(assessmentProto_);
            Double issueProto_ = (((GenerateOutputData) other).getIssue() == null ? 0.0 : ((GenerateOutputData) other).getIssue().doubleValue());
            result_.setIssue(issueProto_);
        }
        return result_.build();
    }

    static List<proto.GenerateOutputData> toProto(List<GenerateOutputData> other) {
        if (other == null) {
            return null;
        } else {
            return other.stream().map(o -> toProto(o)).collect(java.util.stream.Collectors.toList());
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Decision")
    String getDecision();

    @com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    java.lang.Number getAssessment();

    @com.fasterxml.jackson.annotation.JsonGetter("Issue")
    java.lang.Number getIssue();

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
