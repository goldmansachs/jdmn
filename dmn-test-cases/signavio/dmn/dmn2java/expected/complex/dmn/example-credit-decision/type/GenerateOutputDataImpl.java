package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "generateOutputData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GenerateOutputDataImpl implements GenerateOutputData {
        private String decision;
        private java.lang.Number assessment;
        private java.lang.Number issue;

    public GenerateOutputDataImpl() {
    }

    public GenerateOutputDataImpl(java.lang.Number assessment, String decision, java.lang.Number issue) {
        this.setAssessment(assessment);
        this.setDecision(decision);
        this.setIssue(issue);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Assessment")
    public java.lang.Number getAssessment() {
        return this.assessment;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Assessment")
    public void setAssessment(java.lang.Number assessment) {
        this.assessment = assessment;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Decision")
    public String getDecision() {
        return this.decision;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Decision")
    public void setDecision(String decision) {
        this.decision = decision;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Issue")
    public java.lang.Number getIssue() {
        return this.issue;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Issue")
    public void setIssue(java.lang.Number issue) {
        this.issue = issue;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
